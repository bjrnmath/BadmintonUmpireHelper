package com.example.badmintonumpirestandalone.model

import java.io.Serializable
import java.lang.IllegalStateException

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

    abstract fun printTeamA(): String
    abstract fun printTeamB(): String
    abstract fun printStartWording(format: String): String

    infix fun getPlayerName(player: PlayerIDs) = when(player) {
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
        return !(sets.size == 2 &&
                (sets.all { it.points.last().pointA > it.points.last().pointA} ||
                        sets.all { it.points.last().pointB > it.points.last().pointB}) ||
                sets.size == 3)
    }

    fun startNextSet(serve: PlayerIDs, accept: PlayerIDs) {
        sets.add(MatchSet(this,
            PlayerIDs.TEAMAPLAYER1,
            PlayerIDs.TEAMBPLAYER1,
            !sets.last().teamARight
        ))
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

    override fun toString(): String {
        return "Match(playerTeamA=$playerTeamA, playerTeamB=$playerTeamB, sets=$sets)"
    }




}