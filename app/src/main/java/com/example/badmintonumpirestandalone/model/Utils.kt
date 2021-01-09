package com.example.badmintonumpirestandalone.model


// TODO make this configurable in the future
class Utils {
    companion object {
        const val PREFERENCESMATCHKEY = "match-key"
        const val PREFERENCESISELEVEN= "iseleven-key"
        const val PREFERENCESNAME = "badminton-umpire-helper-standalone-preferences"

        const val TWENTYONESETMAX = 30
        const val TWENTYONESETSTANDARD = 21 // the usual number of points when the set ends
        const val TWENTYONEMAXSETCOUNT = 3 // maximal number of matches per game

        const val ELEVENSETMAX = 15
        const val ELEVENSETSTANDARD = 11 // the usual number of points when the set ends
        const val ELEVENMAXSETCOUNT = 5 // maximal number of matches per game

        fun currentGameSetMiddle() = currentGameSetStandard/2 + 1
        var currentGameSetMax = TWENTYONESETMAX
        var currentGameSetStandard = TWENTYONESETSTANDARD
        fun currentGameSetNearEnd() = currentGameSetStandard - 1
        fun currentGameSetNearEnd2() = currentGameSetMax - 1
        var currentGameMaxSetCount = TWENTYONEMAXSETCOUNT
        fun currentGameWinnerSetCount() = currentGameMaxSetCount/2 + 1

        const val BUTTONDELAYMILLIS = 1L
        const val BREAKTIMESETMIDDLEMILLIS = 60L * 1000L
        const val BREAKTIMESETFINISHMILLIS = 2L * 60L * 1000L
        const val WARMUPTIMEMILLIS = 5L * 60L * 1000L
        const val FIELDANNOUNCESOONSTART = 25L * 1000L // show announce text already at 25 seconds to have some time for speaking
        const val TICKMILLIS = 1000L
    }
}