package com.example.badmintonumpirestandalone.model

import java.io.Serializable

abstract class Match(val playerTeamA: List<String>, val playerTeamB: List<String>): Serializable {
    val pointsTeamA = 0
    val pointsTeamB = 0

    abstract fun printTeamA(): String
    abstract fun printTeamB(): String
}