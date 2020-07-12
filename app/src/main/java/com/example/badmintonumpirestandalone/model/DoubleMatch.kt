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
        return format.format(
            getPlayer(set.playerRightEven),
            getPlayer(set.playerRightUneven),
            getPlayer(set.playerLeftEven),
            getPlayer(set.playerLeftUneven),
            getPlayer(set.points[0].serve),
            getPlayer(set.points[0].accept)
            )
    }
}