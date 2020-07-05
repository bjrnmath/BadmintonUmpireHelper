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

    override fun printStartWording(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(set.rightSide[0], set.rightSide[1], set.leftSide[0], set.leftSide[1],
            getPlayer(set.serve), getPlayer(set.accept))
    }
}