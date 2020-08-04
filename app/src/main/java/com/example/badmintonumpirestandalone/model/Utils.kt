package com.example.badmintonumpirestandalone.model


// TODO make this configurable in the future
class Utils {
    companion object {
        val SETMIDDLE = 11
        val SETMAX = 30
        val SETSTANDARD = 21 // the usual number of points when the set ends
        val SETNEAREND = 20
        val SETNEAREND2 = 29
        val BUTTONDELAYMILLIS = 1L
        val BREAKTIMESETMIDDLEMILLIS = 60L * 1000L
        val BREAKTIMESETFINISHMILLIS = 2L * 60L * 1000L
        val WARMUPTIMEMILLIS = 5L * 60L * 1000L
        val FIELDANNOUNCESOONSTART = 25L * 1000L // show announce text already at 25 seconds to have some time for speaking
        val TICKMILLIS = 1000L
    }
}