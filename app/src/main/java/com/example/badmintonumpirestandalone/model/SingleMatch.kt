package com.example.badmintonumpirestandalone.model

class SingleMatch(
    playerTeamA: List<String>,
    playerTeamB: List<String>,
    teamMatch: Boolean,
    teamA: String,
    teamB: String
): Match(playerTeamA, playerTeamB, teamMatch, teamA, teamB){

    override fun printTeamA(): String {
        return playerTeamA[0]
    }

    override fun printTeamB(): String {
        return playerTeamB[0]
    }

    override fun printTeamAPretty(and: String): String {
        return printTeamA()
    }

    override fun printTeamBPretty(and: String): String {
        return printTeamB()
    }

    override fun addPointLeft() {
        currentSet().addPointLeftSingle()
        swapSidesIfNecessary()
    }

    override fun addPointRight() {
        currentSet().addPointRightSingle()
        swapSidesIfNecessary()
    }

    override fun getPlayerFromNumber(number: Int): PlayerIDs {
        return if (number < PlayerIDs.values().size) {PlayerIDs.values()[number * 2]} else {PlayerIDs.UNDEF}
    }

    override fun printStartWording(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(
            getPlayerNameFrom(set.playerRightEven),
            getPlayerNameFrom(set.playerLeftEven),
            getPlayerNameFrom(set.points[0].serve)
        )
    }

    override fun printStartWordingTeam(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(
            teamRight(),
            getPlayerNameFrom(set.playerRightEven),
            teamLeft(),
            getPlayerNameFrom(set.playerLeftEven),
            getPlayerNameFrom(set.points[0].serve)
        )
    }
}