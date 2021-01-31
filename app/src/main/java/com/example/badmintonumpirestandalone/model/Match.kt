package com.example.badmintonumpirestandalone.model

import android.util.Base64
import android.util.Base64.encodeToString
import kotlin.Pair
import java.io.*
import java.lang.StringBuilder


// TODO this could possibly be handled better
enum class PlayerIDs {
    TEAMAPLAYER1,
    TEAMAPLAYER2,
    TEAMBPLAYER1,
    TEAMBPLAYER2,
    UNDEF
}

abstract class Match(
    val playerTeamA: List<String>,
    val playerTeamB: List<String>,
    val teamMatch: Boolean,
    private val teamAName: String,
    private val teamBName: String

): Serializable {
    var sets = mutableListOf<MatchSet>()
    val fieldNumber = 1 //TODO in future this needs to be set

    abstract fun printTeamA(): String
    abstract fun printTeamAPretty(and: String): String
    abstract fun printTeamB(): String
    abstract fun printTeamBPretty(and: String): String
    abstract fun printStartWording(format: String): String
    abstract fun printStartWordingTeam(format: String): String

    fun teamLeft() = if (this.currentSet().teamARight) teamBName else teamAName
    fun teamRight() = if (this.currentSet().teamARight) teamAName else teamBName

    /**
     * For a new match the number of sets must be 1 and the current points must be 0 on both sides.
     */
    fun isNewMatch() = (this.sets.size <= 1) &&
            (this.currentSet().getCurrentPointsLeft() == 0) &&
            (this.currentSet().getCurrentPointsRight() == 0)
    fun isSetNotEnd() = currentSet().checkEnd() && !isSetGivenUp()

    /**
     * Checks if the set is over because one player was injured, surrendered or was disqualified
     */
    fun isSetGivenUp() = currentSet().points.last().incidents.firstOrNull { it.first.isMatchEndIncident() } != null

    fun isFinished() = !(isSetNotEnd() || isNewMatch()) || isSetGivenUp()

    infix fun getPlayerNameFrom(player: PlayerIDs) = when(player) {
        PlayerIDs.TEAMAPLAYER1 -> playerTeamA[0]
        PlayerIDs.TEAMAPLAYER2 -> if (playerTeamA.size > 1) playerTeamA[1] else ""
        PlayerIDs.TEAMBPLAYER1 -> playerTeamB[0]
        PlayerIDs.TEAMBPLAYER2 -> if (playerTeamB.size > 1) playerTeamB[1] else ""
        else -> throw IllegalStateException("Requested player is undefined.")
    }

    fun printWarningString(warning: String, warning_fault: String): String {
        val incidents = currentSet().points.last().incidents
        return if (incidents.isNotEmpty()) {
            val lastIncident = incidents.last()
            if (lastIncident.first == Incidents.WARNING) {
                "${warning.format(getPlayerNameFrom(lastIncident.second))} "
            } else {
                if (lastIncident.first == Incidents.WARNING_ERROR) {
                    "${warning_fault.format(getPlayerNameFrom(lastIncident.second))} "
                } else {
                    ""
                }
            }
        } else {
            ""
        }
    }

    /**
     * Returns true if based on the current points a next set is possible, even if the current set is not yet finished.
     */
    fun nextSetExists(): Boolean {
        // either in two sets one team was winning or it is the third set
        return !(
                (sets.size >= Utils.currentGameWinnerSetCount() &&
                        (sets.all { it.points.last().pointA > it.points.last().pointB} ||
                        sets.all { it.points.last().pointB > it.points.last().pointA}) ||
                sets.size == Utils.currentGameMaxSetCount)
                ) && !isSetGivenUp()
    }

    fun startNextSet(serve: PlayerIDs, accept: PlayerIDs) {
        sets.add(MatchSet(this,
            serve,
            accept,
            !sets.last().teamARight
        ))
    }

    fun getSetWinner():Pair<PlayerIDs, PlayerIDs> {
        return if (currentSet().teamARight && currentSet().getCurrentPointsRight() > currentSet().getCurrentPointsLeft() ||
            !currentSet().teamARight && currentSet().getCurrentPointsRight() < currentSet().getCurrentPointsLeft()) {
             Pair(PlayerIDs.TEAMAPLAYER1, PlayerIDs.TEAMAPLAYER2)
        } else {
            Pair(PlayerIDs.TEAMBPLAYER1, PlayerIDs.TEAMBPLAYER2)
        }
    }

    fun getSetLoser():Pair<PlayerIDs, PlayerIDs> {
        return if (currentSet().teamARight && currentSet().getCurrentPointsRight() > currentSet().getCurrentPointsLeft() ||
            !currentSet().teamARight && currentSet().getCurrentPointsRight() < currentSet().getCurrentPointsLeft()) {
            Pair(PlayerIDs.TEAMBPLAYER1, PlayerIDs.TEAMBPLAYER2)
        } else {
            Pair(PlayerIDs.TEAMAPLAYER1, PlayerIDs.TEAMAPLAYER2)
        }
    }

    fun currentSet(): MatchSet {
        return sets.last()
    }

    fun isTeamA(player: PlayerIDs) = when(player) {
        PlayerIDs.TEAMAPLAYER1, PlayerIDs.TEAMAPLAYER2 -> true
        PlayerIDs.TEAMBPLAYER1, PlayerIDs.TEAMBPLAYER2-> false
        else -> throw IllegalStateException("Requested player is undefined.")
    }

    fun getTeamMate(player: PlayerIDs) = when(player) {
        PlayerIDs.TEAMAPLAYER1 -> PlayerIDs.TEAMAPLAYER2
        PlayerIDs.TEAMAPLAYER2 -> PlayerIDs.TEAMAPLAYER1
        PlayerIDs.TEAMBPLAYER1 -> PlayerIDs.TEAMBPLAYER2
        PlayerIDs.TEAMBPLAYER2 -> PlayerIDs.TEAMBPLAYER1
        else -> throw IllegalStateException("Requested player is undefined.")
    }

    /**
     * Returns the current points string as to announce correctly, determines if setpoint or matchpoint should be
     * included in the string.
     */
    fun getCurrentPointsString(bothString: String, setPointString: String, matchPointString: String): String {
        return currentSet().getCurrentPointsString(
            bothString,
            if (nextSetExists())

                setPointString
            else
                matchPointString
        )
    }

    override fun toString(): String {
        return "Match(playerTeamA=$playerTeamA, playerTeamB=$playerTeamB, sets=$sets)"
    }

    fun undo(): Boolean {
        if (currentSet().points.last().incidents.size > 0) {
            currentSet().points.last().incidents.clear()
            return true
        }
        if (currentSet().points.size > 1) {
            currentSet().undo()
            return true
        } else {
            if (sets.size > 1) {
                sets.removeAt(sets.lastIndex)
                // also remove last made point here
                currentSet().undo()
                return true
            }
            // if nothing was done by now, just ignore the undo for now
        }
        return false
    }

    abstract fun addPointLeft()
    abstract fun addPointRight()

    /**
     * Returns the playerID based on the given number. For double matches there are 4 numbers, for singles only 2.
     */
    abstract fun getPlayerFromNumber(number: Int): PlayerIDs

    fun swapSidesIfNecessary() {
        if (sets.size == Utils.currentGameMaxSetCount && currentSet().isBreak()) {
            currentSet().swapSides()
        }
    }

    private fun isWinnerA():Boolean {
        return if (!isSetGivenUp()) {
            sets.filter {
                isWinnerA(it)
            }.count() >= Utils.currentGameWinnerSetCount()
        } else {
            val endMatchIncident = currentSet().points.last().incidents.first { it.first.isMatchEndIncident() }
            !isTeamA(endMatchIncident.second)
        }


    }

    private fun isWinnerA(currentSet: MatchSet) =
        currentSet.teamARight && currentSet.getCurrentPointsRight() > currentSet.getCurrentPointsLeft() ||
                !currentSet.teamARight && currentSet.getCurrentPointsRight() < currentSet.getCurrentPointsLeft()

    fun printGivenUp(givenUpString: String, disqualificationString: String): String {
        val gameEndIncident = currentSet().points.last().incidents.first { it.first.isMatchEndIncident() }
        val playerName = getPlayerNameFrom(gameEndIncident.second)
        return if (gameEndIncident.first == Incidents.SURRENDER || gameEndIncident.first == Incidents.INJURY) {
            givenUpString.format(playerName)
        } else {
            disqualificationString.format(playerName)
        }
    }

    fun printWinWording(string: String, and: String): CharSequence {
        val winnerA = isWinnerA()
        val winnerTeam = printWinnerTeamPretty(winnerA, and)
        val points = sets.joinToString(", ") {
            printWinnerPointsPretty(winnerA, it)
        }
        return string.format(winnerTeam, points)
    }

    private fun printWinnerPointsPretty(
        winnerA: Boolean,
        currentSet: MatchSet
    ): CharSequence {
        return if (winnerA && currentSet.teamARight || !winnerA && !currentSet.teamARight)
            "${currentSet.getCurrentPointsRight()}-${currentSet.getCurrentPointsLeft()}"
        else
            "${currentSet.getCurrentPointsLeft()}-${currentSet.getCurrentPointsRight()}"
    }

    private fun printWinnerTeamPretty(winnerA: Boolean, and: String) =
        if (winnerA)
            if (teamMatch)
                teamAName
            else
                printTeamAPretty(and)
        else
            if (teamMatch)
                teamBName
            else
                printTeamBPretty(and)

    fun nextSetAnnounce(nextSetAnnounce: String, and: String): CharSequence {
        val winnerA = isWinnerA(currentSet())
        return nextSetAnnounce.format(
            printWinnerTeamPretty(winnerA, and),
            printWinnerPointsPretty(winnerA, currentSet())
        )
    }

    /**
     * Stores the given incident.
     */
    fun saveIncident(incident: Incidents, detail: Int) {
        if (incident == Incidents.WARNING_ERROR) {
            val playerID = getPlayerFromNumber(detail)
            if (isTeamA(playerID) && currentSet().teamARight || !isTeamA(playerID) && !currentSet().teamARight) {
                addPointLeft()
            } else {
                addPointRight()
            }
        }
        if (incident != Incidents.CHOOSE) {
            val lastPoint = currentSet().points.last()
            lastPoint.incidents.add(Pair(incident, getPlayerFromNumber(if (detail > 0) {detail} else {0})))
        }
    }

    fun toSerializedString(): String {
        val arrayOut = ByteArrayOutputStream()
        val out = ObjectOutputStream(arrayOut)
        out.writeObject(this)
        out.close()
        return encodeToString(arrayOut.toByteArray(),0) ?: ""
    }

    fun printAllPoints(): String {
        return this.sets.joinToString(separator = "\n") { "${it.points.last().pointA} - ${it.points.last().pointB}" }
    }

    fun listIncidents(incidentStrings: Array<String>): CharSequence {
        val incidents = StringBuilder()
        sets.forEach { incidents.append(it.listIncidents(incidentStrings)) }
        return incidents.toString()
    }

    /**
     * Checks if based on the current points and sets both teams have equally won sets.
     */
    fun isEqualSets(): Boolean {
        var winnerA = 0
        var winnerB = 0
        sets.forEach { if (isWinnerA(it)) {winnerA++} else {winnerB++} }
        return winnerA == winnerB
    }

    companion object {
        fun fromSerializedString(matchString: String): Match? {
            if (matchString.isEmpty()) {
                return null
            }
            val bytes: ByteArray = Base64.decode(matchString, 0)
            return try {
                val objectInputStream = ObjectInputStream(ByteArrayInputStream(bytes))
                objectInputStream.readObject() as? Match
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}