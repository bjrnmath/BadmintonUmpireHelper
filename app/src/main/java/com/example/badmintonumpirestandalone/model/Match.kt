package com.example.badmintonumpirestandalone.model

import java.io.Serializable

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
    override fun toString(): String {
        return "Match(playerTeamA=$playerTeamA, playerTeamB=$playerTeamB, pointsTeamA=$pointsTeamA, pointsTeamB=$pointsTeamB, sets=$sets)"
    }


}