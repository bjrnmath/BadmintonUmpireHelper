package com.example.badmintonumpirestandalone

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.badmintonumpirestandalone.model.Utils
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


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
     * Checks if the slider double is activated the respective name fields appear and disappear on next click.
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

        UtilityFunctions.clickElement(R.id.Switch_Single_Double)

        UtilityFunctions.isVisible(R.id.player1)
        UtilityFunctions.isVisible(R.id.player3)
        UtilityFunctions.isNotVisible(R.id.player2)
        UtilityFunctions.isNotVisible(R.id.player4)


    }

    /**
     * Checks if the slider team match is activated the respective name fields appear and disappear on next click.
     */
    @Test
    fun checkSliderTeamMatch() {
        UtilityFunctions.isNotVisible(R.id.teamA_name)
        UtilityFunctions.isNotVisible(R.id.teamB_name)

        UtilityFunctions.clickElement(R.id.Switch_Tournament_Team)

        UtilityFunctions.isVisible(R.id.teamA_name)
        UtilityFunctions.isVisible(R.id.teamB_name)

        UtilityFunctions.clickElement(R.id.Switch_Tournament_Team)

        UtilityFunctions.isNotVisible(R.id.teamA_name)
        UtilityFunctions.isNotVisible(R.id.teamB_name)
    }

    /**
     * Checks if the slider team match is activated the respective name fields appear and disappear on next click.
     */
    @Test
    fun checkSliderMaxCount() {
        Assert.assertEquals(21, Utils.currentGameSetStandard)
        Assert.assertEquals(30, Utils.currentGameSetMax)
        Assert.assertEquals(3, Utils.currentGameMaxSetCount)

        UtilityFunctions.clickElement(R.id.Switch_Counting)

        Assert.assertEquals(11, Utils.currentGameSetStandard)
        Assert.assertEquals(15, Utils.currentGameSetMax)
        Assert.assertEquals(5, Utils.currentGameMaxSetCount)

        UtilityFunctions.clickElement(R.id.Switch_Counting)

        Assert.assertEquals(21, Utils.currentGameSetStandard)
        Assert.assertEquals(30, Utils.currentGameSetMax)
        Assert.assertEquals(3, Utils.currentGameMaxSetCount)
    }
}
