package com.example.badmintonumpirestandalone.model

class SingleMatch(playerTeamA: List<String>, playerTeamB: List<String>): Match(playerTeamA, playerTeamB){

    override fun printTeamA(): String {
        return playerTeamA[0]
    }

    override fun printTeamB(): String {
        return playerTeamB[0]
    }

    override fun printStartWording(format: String): String {
        // TODO add existence checks and do proper error handling here
        val set = sets[0]
        return format.format(getPlayer(set.playerRightEven), getPlayer(set.playerLeftEven), getPlayer(set.points[0].serve))
    }
}