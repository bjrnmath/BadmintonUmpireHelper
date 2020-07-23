package com.example.badmintonumpirestandalone.model

import java.io.Serializable
import kotlin.math.abs

data class SetPoint(val pointA: Int, val pointB: Int, val serve: PlayerIDs, val accept: PlayerIDs): Serializable

class MatchSet( private val match: Match,
                serve: PlayerIDs,
                accept: PlayerIDs,
                var teamARight: Boolean
): Serializable {

    var playerLeftEven = if ((teamARight && !match.isTeamA(serve)) || (!teamARight && match.isTeamA(serve))) serve else accept
    // player right even is the opposite of player left even
    var playerRightEven = if ((teamARight && !match.isTeamA(serve)) || (!teamARight && match.isTeamA(serve))) accept else serve
    var playerLeftUneven = match.getTeamMate(playerLeftEven)
    var playerRightUneven = match.getTeamMate(playerRightEven)

    val points = mutableListOf(SetPoint(0, 0, serve, accept))

    fun serveChanged() = points.size > 1 && points.last().serve != points[points.size - 2].serve

    fun getCurrentPointsLeft() = if (teamARight) points.last().pointB else points.last().pointA
    fun getCurrentPointsRight() = if (teamARight) points.last().pointA else points.last().pointB
    fun isServeLeft() =
        (match.isTeamA(points.last().serve) && !teamARight) || (!match.isTeamA(points.last().serve) && teamARight)
    fun isBreak() = points.size > 1 && (getCurrentPointsLeft() == Utils.SETMIDDLE || getCurrentPointsRight() == Utils.SETMIDDLE) &&
            points[points.size - 2].pointA < Utils.SETMIDDLE && points[points.size - 2].pointB < Utils.SETMIDDLE
    private fun isSetNearEnd() = points.size > 1 &&
            //set near end either happens at Utils.SETNEAREND once or at Utils.SETNEAREND2 always
            ((getCurrentPointsLeft() == Utils.SETNEAREND || getCurrentPointsRight() == Utils.SETNEAREND) &&
            points[points.size - 2].pointA < Utils.SETNEAREND && points[points.size - 2].pointB < Utils.SETNEAREND ||
            (getCurrentPointsLeft() == Utils.SETNEAREND2 || getCurrentPointsRight() == Utils.SETNEAREND2))

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

        return !(((state.pointA >= Utils.SETSTANDARD || state.pointB >= Utils.SETSTANDARD) &&
                abs(state.pointA - state.pointB) >= 2) ||
                (state.pointA >= Utils.SETMAX || state.pointB >= Utils.SETMAX))
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

    /**
     * Return the current points as string, including given setEndAnnounce (either setpoint or matchpoint) and
     * "both" if the points are equal.
     */
    fun getCurrentPointsString(bothString: String, setPointString: String): String {
        val setEndAnnounceString = if (isSetNearEnd()) {
            setPointString
        } else {
            if (getCurrentPointsLeft() == getCurrentPointsRight()) {
                ""
            } else {
                "-"
            }
        }

        // if both have the same points
        if (getCurrentPointsLeft() == getCurrentPointsRight()) {
            return "${getCurrentPointsLeft()} $setEndAnnounceString $bothString"
        }

        // if the points are different, setEndAnnounceString contains the '-' character if it is not close to the set end
        return if (isServeLeft()) {
            "${getCurrentPointsLeft()} $setEndAnnounceString ${getCurrentPointsRight()}"
        } else {
            "${getCurrentPointsRight()} $setEndAnnounceString ${getCurrentPointsLeft()}"
        }
    }

    fun undo() {
        if (isBreak()) {
            // undo the side swapping by swapping sides again
            swapSides()
        }
        points.removeAt(points.lastIndex)
    }

    fun swapSides() {
        val tmpLeftEven = playerLeftEven
        val tmpLeftUneven = playerLeftUneven
        playerLeftEven = playerRightEven
        playerLeftUneven = playerRightUneven
        playerRightEven = tmpLeftEven
        playerRightUneven = tmpLeftUneven
        teamARight = !teamARight
    }


}
