package com.example.badmintonumpirestandalone

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.not

class UtilityFunctions {

    companion object{
        fun isVisible(id: Int) {
            onView(withId(id)).check(matches(isDisplayed()))
        }

        fun isNotVisible(id: Int) {
            onView(withId(id)).check(matches(not(isDisplayed())))
        }

        fun clickElement(id: Int) {
            onView(withId(id)).perform(click())
        }
    }
}