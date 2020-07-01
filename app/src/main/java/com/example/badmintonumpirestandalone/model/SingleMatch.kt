package com.example.badmintonumpirestandalone.model

class SingleMatch(playerTeamA: List<String>, playerTeamB: List<String>): Match(playerTeamA, playerTeamB){

    override fun printTeamA(): String {
        return playerTeamA[0]
    }

    override fun printTeamB(): String {
        return playerTeamB[0]
    }

}