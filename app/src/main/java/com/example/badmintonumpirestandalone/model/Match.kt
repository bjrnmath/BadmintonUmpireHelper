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
    val pointsTeamA = 0
    val pointsTeamB = 0
    var sets = mutableListOf<MatchSet>()

    abstract fun printTeamA(): String
    abstract fun printTeamB(): String
    abstract fun printStartWording(format: String): String

    fun getPlayer(player: PlayerIDs) = when(player) {
        PlayerIDs.TEAMAPLAYER1 -> playerTeamA[0]
        PlayerIDs.TEAMAPLAYER2 -> playerTeamA[1]
        PlayerIDs.TEAMBPLAYER1 -> playerTeamB[0]
        PlayerIDs.TEAMBPLAYER2 -> playerTeamB[1]
        else -> throw IllegalStateException("Requested player is undefined.")
    }

    override fun toString(): String {
        return "Match(playerTeamA=$playerTeamA, playerTeamB=$playerTeamB, pointsTeamA=$pointsTeamA, pointsTeamB=$pointsTeamB, sets=$sets)"
    }




}