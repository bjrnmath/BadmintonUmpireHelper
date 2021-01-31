package com.example.badmintonumpirestandalone

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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

        fun fillWithText(id: Int, newText: String) {
            onView(withId(id)).perform(replaceText(newText))
        }

        fun hasText(id: Int, text: String) {
            onView(withId(id)).check(matches(withText(text)))
        }
    }
}