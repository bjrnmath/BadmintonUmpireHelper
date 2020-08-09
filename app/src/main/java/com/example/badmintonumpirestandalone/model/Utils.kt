package com.example.badmintonumpirestandalone.model


// TODO make this configurable in the future
class Utils {
    companion object {
        const val PREFERENCESMATCHKEY = "match-key"
        const val PREFERENCESNAME = "badminton-umpire-helper-standalone-preferences"
        const val SETMIDDLE = 11
        const val SETMAX = 30
        const val SETSTANDARD = 21 // the usual number of points when the set ends
        const val SETNEAREND = 20
        const val SETNEAREND2 = 29
        const val BUTTONDELAYMILLIS = 1L
        const val BREAKTIMESETMIDDLEMILLIS = 60L * 1000L
        const val BREAKTIMESETFINISHMILLIS = 2L * 60L * 1000L
        const val WARMUPTIMEMILLIS = 5L * 60L * 1000L
        const val FIELDANNOUNCESOONSTART = 25L * 1000L // show announce text already at 25 seconds to have some time for speaking
        const val TICKMILLIS = 1000L
    }
}