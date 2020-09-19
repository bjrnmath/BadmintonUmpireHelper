package com.example.badmintonumpirestandalone.model

enum class Incidents {
    // NOTE: Has to be in line with values/arrays.xml -> incidents string array to match the correct positions!
    CHOOSE,
    REFEREE,
    WARNING,
    WARNING_ERROR,
    DISQUALIFICATION,
    SURRENDER,
    INJURY,
    OVERRULING,
    CORRECTION,
    SWITCH_FIELD,
    SWITCH_SERVE
}