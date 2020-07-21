package com.example.badmintonumpirestandalone.model

import java.io.Serializable
import kotlin.math.abs

data class SetPoint(val pointA: Int, val pointB: Int, val serve: PlayerIDs, val accept: PlayerIDs): Serializable

class MatchSet( private val match: Match,
                serve: PlayerIDs,
                accept: PlayerIDs,
                val teamARight: Boolean
): Serializable {

    var playerLeftEven = if (!teamARight && match.isTeamA(serve)) serve else accept
    var playerLeftUneven = if (!teamARight && match.isTeamA(serve)) match.getTeamMate(serve) else match.getTeamMate(accept)
    var playerRightEven = if (teamARight && match.isTeamA(serve)) serve else accept
    var playerRightUneven = if (teamARight && match.isTeamA(serve)) match.getTeamMate(serve) else match.getTeamMate(accept)

    val points = mutableListOf(SetPoint(0, 0, serve, accept))

    val SETEND = 21 // Number of points needed to win the set
    val MAXPOINTS = 30 // Maximal number of points per set

    fun getCurrentPointsLeft() = if (teamARight) points.last().pointB else points.last().pointA
    fun getCurrentPointsRight() = if (teamARight) points.last().pointA else points.last().pointB
    fun isServeLeft() =
        (match.isTeamA(points.last().serve) && !teamARight) || (!match.isTeamA(points.last().serve) && teamARight)

    /**
     * Returns true if the set is not finished yet, i.e. no team won by now.
     */
    fun addPointLeft():Boolean {
        val currentSetPoint = points.last()
        val pointA = currentSetPoint.pointA + if (teamARight) 0 else 1
        val pointB = currentSetPoint.pointB + if (teamARight) 1 else 0

        val serve: PlayerIDs
        val accept: PlayerIDs
        if (isServeLeft()) {
            switchLeft()
            serve = currentSetPoint.serve
            accept = match.getTeamMate(currentSetPoint.accept)
        } else {
            serve =  if (getCurrentPointsLeft() % 2 == 0) playerLeftEven else playerLeftUneven
            accept =  if (getCurrentPointsLeft() % 2 == 0) playerRightEven else playerRightUneven
        }

        points.add(SetPoint(pointA, pointB, serve, accept))
        return checkEnd()
    }

    /**
     * Returns true if the set is not finished yet, i.e. no team won by now.
     */
    fun addPointRight():Boolean {
        val currentSetPoint = points.last()
        val pointA = currentSetPoint.pointA + if (teamARight) 1 else 0
        val pointB = currentSetPoint.pointB + if (teamARight) 0 else 1

        val serve: PlayerIDs
        val accept: PlayerIDs
        if (!isServeLeft()) {
            switchRight()
            serve = currentSetPoint.serve
            accept = match.getTeamMate(currentSetPoint.accept)
        } else {
            serve =  if (getCurrentPointsRight() % 2 == 0) playerRightEven else playerRightUneven
            accept =  if (getCurrentPointsRight() % 2 == 0) playerLeftEven else playerLeftUneven
        }

        points.add(SetPoint(pointA, pointB, serve, accept))
        return checkEnd()
    }

    private fun checkEnd(): Boolean {
        val state = points.last()

        return !((state.pointA >= SETEND || state.pointB >= SETEND) &&
                abs(state.pointA - state.pointB) >= 2) ||
                (state.pointA >= MAXPOINTS || state.pointB >= MAXPOINTS)
    }

    private fun switchLeft() {
        val tmp = playerLeftUneven
        playerLeftUneven = playerLeftEven
        playerLeftEven = tmp
    }

    private fun switchRight() {
        val tmp = playerRightUneven
        playerRightUneven = playerRightEven
        playerRightEven = tmp
    }

    override fun toString(): String {
        return "MatchSet(player_left_even=$playerLeftEven, player_left_uneven=$playerLeftUneven, player_right_even=$playerRightEven, player_right_uneven=$playerRightUneven, points=${points.last()})"
    }


}
