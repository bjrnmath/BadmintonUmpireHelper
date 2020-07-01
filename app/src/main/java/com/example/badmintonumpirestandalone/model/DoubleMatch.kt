package com.example.badmintonumpirestandalone.model

class DoubleMatch(playerTeamA: List<String>, playerTeamB: List<String>): Match(playerTeamA, playerTeamB) {

    private fun printTeam(team: List<String>): String {
        return "${team[0]}/${team[1]}"
    }

    override fun printTeamA(): String {
        return printTeam(playerTeamA)
    }

    override fun printTeamB(): String {
        return printTeam(playerTeamB)
    }
}