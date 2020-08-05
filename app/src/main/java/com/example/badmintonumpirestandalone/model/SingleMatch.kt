package com.example.badmintonumpirestandalone.model

class SingleMatch(playerTeamA: List<String>, playerTeamB: List<String>): Match(playerTeamA, playerTeamB){

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

    override fun addPointLeft(): Boolean {
        val endSet = currentSet().addPointLeftSingle()
        swapSidesIfNecessary()

        return endSet
    }

    override fun addPointRight(): Boolean {
        val endSet = currentSet().addPointRightSingle()
        swapSidesIfNecessary()

        return endSet
    }

    override fun printStartWording(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(getPlayerNameFrom(set.playerRightEven), getPlayerNameFrom(set.playerLeftEven), getPlayerNameFrom(set.points[0].serve))
    }
}