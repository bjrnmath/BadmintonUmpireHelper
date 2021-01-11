package com.example.badmintonumpirestandalone

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.not


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Checks if the slider double is activated the respective name fields appear.
     */
    @Test
    fun checkSliderDouble() {
        UtilityFunctions.isVisible(R.id.player1)
        UtilityFunctions.isVisible(R.id.player3)
        UtilityFunctions.isNotVisible(R.id.player2)
        UtilityFunctions.isNotVisible(R.id.player4)

        UtilityFunctions.clickElement(R.id.Switch_Single_Double)

        UtilityFunctions.isVisible(R.id.player1)
        UtilityFunctions.isVisible(R.id.player3)
        UtilityFunctions.isVisible(R.id.player2)
        UtilityFunctions.isVisible(R.id.player4)

    }
}
