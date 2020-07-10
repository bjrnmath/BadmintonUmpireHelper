package com.example.badmintonumpirestandalone.model

import java.io.Serializable


class MatchSet( match: Match,
                var serve: PlayerIDs,
                var accept: PlayerIDs,
                val teamARight: Boolean
): Serializable {

    var player_left_even = if (!teamARight && match.isTeamA(serve)) serve else accept
    var player_left_uneven = if (!teamARight && match.isTeamA(serve)) match.getTeamMate(serve) else match.getTeamMate(accept)
    var player_right_even = if (teamARight && match.isTeamA(serve)) serve else accept
    var player_right_uneven = if (teamARight && match.isTeamA(serve)) match.getTeamMate(serve) else match.getTeamMate(accept)

    private var pointsTeamA = 0
    private var pointsTeamB = 0

    override fun toString(): String {
        return "MatchSet(player_left_even=$player_left_even, player_left_uneven=$player_left_uneven, player_right_even=$player_right_even, player_right_uneven=$player_right_uneven, pointsTeamA=$pointsTeamA, pointsTeamB=$pointsTeamB)"
    }


}