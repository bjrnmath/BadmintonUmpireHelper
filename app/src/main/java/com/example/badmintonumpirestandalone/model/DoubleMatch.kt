package com.example.badmintonumpirestandalone.model

class DoubleMatch(
    playerTeamA: List<String>,
    playerTeamB: List<String>,
    teamMatch: Boolean,
    teamA: String,
    teamB: String
): Match(playerTeamA, playerTeamB, teamMatch, teamA, teamB){

    private fun printTeam(team: List<String>, delim: String): String {
        return "${team[0]}$delim${team[1]}"
    }

    override fun printTeamA(): String {
        return printTeam(playerTeamA, "/")
    }

    override fun printTeamB(): String {
        return printTeam(playerTeamB, "/")
    }

    override fun printTeamAPretty(and: String): String {
        return printTeam(playerTeamA, " $and ")
    }

    override fun printTeamBPretty(and: String): String {
        return printTeam(playerTeamB, " $and ")
    }

    override fun addPointLeft() {
        currentSet().addPointLeftDouble()
        swapSidesIfNecessary()
    }

    override fun addPointRight() {
        currentSet().addPointRightDouble()
        swapSidesIfNecessary()
    }

    override fun printStartWording(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(
            getPlayerNameFrom(set.playerRightEven),
            getPlayerNameFrom(set.playerRightUneven),
            getPlayerNameFrom(set.playerLeftEven),
            getPlayerNameFrom(set.playerLeftUneven),
            getPlayerNameFrom(set.points[0].serve),
            getPlayerNameFrom(set.points[0].accept)
            )
    }

    override fun printStartWordingTeam(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(
            teamRight(),
            getPlayerNameFrom(set.playerRightEven),
            getPlayerNameFrom(set.playerRightUneven),
            teamLeft(),
            getPlayerNameFrom(set.playerLeftEven),
            getPlayerNameFrom(set.playerLeftUneven),
            getPlayerNameFrom(set.points[0].serve),
            getPlayerNameFrom(set.points[0].accept)
        )
    }
}