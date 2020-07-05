package com.example.badmintonumpirestandalone.model

import java.io.Serializable


class MatchSet(val serve: PlayerIDs, val accept: PlayerIDs, val rightSide: List<String>, val leftSide: List<String>): Serializable {
    override fun toString(): String {
        return "MatchSet(serve=$serve, accept=$accept, rightSide=$rightSide, leftSide=$leftSide)\n"
    }
}