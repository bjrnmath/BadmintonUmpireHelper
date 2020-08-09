package com.example.badmintonumpirestandalone.model

import android.R.string
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Pair
import java.io.*


// TODO this could possibly be handled better
enum class PlayerIDs {
    TEAMAPLAYER1,
    TEAMAPLAYER2,
    TEAMBPLAYER1,
    TEAMBPLAYER2,
    UNDEF
}

abstract class Match(val playerTeamA: List<String>, val playerTeamB: List<String>): Serializable {
    var sets = mutableListOf<MatchSet>()
    val fieldNumber = 1 //TODO in future this needs to be set

    abstract fun printTeamA(): String
    abstract fun printTeamAPretty(and: String): String
    abstract fun printTeamB(): String
    abstract fun printTeamBPretty(and: String): String
    abstract fun printStartWording(format: String): String

    infix fun getPlayerNameFrom(player: PlayerIDs) = when(player) {
        PlayerIDs.TEAMAPLAYER1 -> playerTeamA[0]
        PlayerIDs.TEAMAPLAYER2 -> if (playerTeamA.size > 1) playerTeamA[1] else ""
        PlayerIDs.TEAMBPLAYER1 -> playerTeamB[0]
        PlayerIDs.TEAMBPLAYER2 -> if (playerTeamB.size > 1) playerTeamB[1] else ""
        else -> throw IllegalStateException("Requested player is undefined.")
    }

    /**
     * Returns true if based on the current points a next set is possible, even if the current set is not yet finished.
     */
    fun nextSetExists(): Boolean {
        // either in two sets one team was winning or it is the third set
        return !(
                (sets.size == 2 &&
                        (sets.all { it.points.last().pointA > it.points.last().pointB} ||
                        sets.all { it.points.last().pointB > it.points.last().pointA}) ||
                sets.size == 3)
                )
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

    abstract fun addPointLeft():Boolean
    abstract fun addPointRight():Boolean

    fun swapSidesIfNecessary() {
        if (sets.size == 3 && currentSet().isBreak()) {
            currentSet().swapSides()
        }
    }

    private fun isWinnerA():Boolean {
        return sets.filter {
            isWinnerA(it)
        }.count() >= 2
    }

    private fun isWinnerA(currentSet: MatchSet) =
        currentSet.teamARight && currentSet.getCurrentPointsRight() > currentSet.getCurrentPointsLeft() ||
                !currentSet.teamARight && currentSet.getCurrentPointsRight() < currentSet.getCurrentPointsLeft()

    fun printWinWording(string: String, and: String): CharSequence? {
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
        if (winnerA) printTeamAPretty(and) else printTeamBPretty(and)

    fun nextSetAnnounce(nextSetAnnounce: String, and: String): CharSequence? {
        val winnerA = isWinnerA(currentSet())
        return nextSetAnnounce.format(
            printWinnerTeamPretty(winnerA, and),
            printWinnerPointsPretty(winnerA, currentSet())
        )
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

    companion object {
        fun fromSerializedString(matchString: String): Match? {
            val bytes: ByteArray = Base64.decode(matchString, 0)
            var match: Match? = null
            try {
                val objectInputStream = ObjectInputStream(ByteArrayInputStream(bytes))
                match = objectInputStream.readObject() as? Match
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return match
        }
    }
}