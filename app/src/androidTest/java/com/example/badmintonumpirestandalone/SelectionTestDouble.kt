package com.example.badmintonumpirestandalone

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before


/**
 * Runs tests to check if the selection view behaves as intended.
 */
@RunWith(AndroidJUnit4::class)
class SelectionTestDouble {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    private val player1String = "Alan"
    private val player2String = "Berta"
    private val player3String = "Charlie"
    private val player4String = "Dan"

    /**
     * Prepares the view for a double match.
     */
    @Before
    fun prepareSelectionField() {
        UtilityFunctions.clickElement(R.id.Switch_Single_Double)

        UtilityFunctions.fillWithText(R.id.player1, player1String)
        UtilityFunctions.fillWithText(R.id.player2, player2String)
        UtilityFunctions.fillWithText(R.id.player3, player3String)
        UtilityFunctions.fillWithText(R.id.player4, player4String)

        UtilityFunctions.clickElement(R.id.start_match)


        UtilityFunctions.clickElement(R.id.randomize)



    }

    /**
     * Checks if everything is initially correctly visible after the coin flip was made.
     */
    @Test
    fun checkInitialView() {
        // check if all text are visible as they should be
        UtilityFunctions.hasText(R.id.vsHeader, "${player1String}/${player2String} vs. ${player3String}/${player4String}")
        UtilityFunctions.hasText(R.id.serveTeamAPlayer1, player1String)
        UtilityFunctions.hasText(R.id.serveTeamAPlayer2, player2String)

        UtilityFunctions.hasText(R.id.serveTeamBPlayer1, player3String)
        UtilityFunctions.hasText(R.id.serveTeamBPlayer2, player4String)

        UtilityFunctions.hasText(R.id.teamASide, "${player1String}/${player2String}")
        UtilityFunctions.hasText(R.id.teamBSide, "${player3String}/${player4String}")

        // check if all buttons are there as they should be
        UtilityFunctions.isVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamASideLeft)
        UtilityFunctions.isVisible(R.id.teamASideRight)

        UtilityFunctions.isVisible(R.id.teamBSideLeft)
        UtilityFunctions.isVisible(R.id.teamBSideRight)
    }

/////////////////////////////////////// Serve/Accept choosing Tests //////////////////////////
    /**
     * Checks if after player 1 in team A chose to serve, all buttons are correctly visible.
     */
    @Test
    fun checkTeamAPlayer1Serve() {
        UtilityFunctions.clickElement(R.id.teamAPlayer1Serve)

        teamAchoseServe()
    }

    /**
     * Checks if after player 2 in team A chose to serve, all buttons are correctly visible.
     */
    @Test
    fun checkTeamAPlayer2Serve() {
        UtilityFunctions.clickElement(R.id.teamAPlayer2Serve)

        teamAchoseServe()
    }

    /**
     * Checks if after player 1 in team A chose to accept, all buttons are correctly visible.
     */
    @Test
    fun checkTeamAPlayer1Accept() {
        UtilityFunctions.clickElement(R.id.teamAPlayer1Accept)

        teamAchoseAccept()
    }

    /**
     * Checks if after player 2 in team A chose to accept, all buttons are correctly visible.
     */
    @Test
    fun checkTeamAPlayer2Accept() {
        UtilityFunctions.clickElement(R.id.teamAPlayer2Accept)

        teamAchoseAccept()
    }

    /**
     * If team A chose serve, the button visibility has to be as defined below.
     */
    private fun teamAchoseServe() {
        // check if all buttons are there as they should be
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamASideLeft)
        UtilityFunctions.isNotVisible(R.id.teamASideRight)
        UtilityFunctions.isVisible(R.id.teamBSideLeft)
        UtilityFunctions.isVisible(R.id.teamBSideRight)
    }

    /**
     * If team A chose accept, the button visibility has to be as defined below.
     */
    private fun teamAchoseAccept() {
        // check if all buttons are there as they should be
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamASideLeft)
        UtilityFunctions.isNotVisible(R.id.teamASideRight)
        UtilityFunctions.isVisible(R.id.teamBSideLeft)
        UtilityFunctions.isVisible(R.id.teamBSideRight)
    }

    /**
     * Checks if after player 1 in team B chose to serve, all buttons are correctly visible.
     */
    @Test
    fun checkTeamBPlayer1Serve() {
        UtilityFunctions.clickElement(R.id.teamBPlayer1Serve)

        teamBchoseServe()
    }

    /**
     * Checks if after player 2 in team B chose to serve, all buttons are correctly visible.
     */
    @Test
    fun checkTeamBPlayer2Serve() {
        UtilityFunctions.clickElement(R.id.teamBPlayer2Serve)

        teamBchoseServe()
    }

    /**
     * Checks if after player 1 in team B chose to accept, all buttons are correctly visible.
     */
    @Test
    fun checkTeamBPlayer1Accept() {
        UtilityFunctions.clickElement(R.id.teamBPlayer1Accept)

        teamBchoseAccept()
    }

    /**
     * Checks if after player 2 in team B chose to accept, all buttons are correctly visible.
     */
    @Test
    fun checkTeamBPlayer2Accept() {
        UtilityFunctions.clickElement(R.id.teamBPlayer2Accept)

        teamBchoseAccept()
    }

    /**
     * If team B chose serve, the button visibility has to be as defined below.
     */
    private fun teamBchoseServe() {
        // check if all buttons are there as they should be
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamASideLeft)
        UtilityFunctions.isVisible(R.id.teamASideRight)
        UtilityFunctions.isNotVisible(R.id.teamBSideLeft)
        UtilityFunctions.isNotVisible(R.id.teamBSideRight)
    }

    /**
     * If team B chose accept, the button visibility has to be as defined below.
     */
    private fun teamBchoseAccept() {
        // check if all buttons are there as they should be
        UtilityFunctions.isVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamASideLeft)
        UtilityFunctions.isVisible(R.id.teamASideRight)
        UtilityFunctions.isNotVisible(R.id.teamBSideLeft)
        UtilityFunctions.isNotVisible(R.id.teamBSideRight)
    }


/////////////////////////////////////// Side choosing Tests //////////////////////////
    /**
     * Checks if team A chose side left, all buttons are correctly visible.
     */
    @Test
    fun checkTeamALeftSide() {
        UtilityFunctions.clickElement(R.id.teamASideLeft)

        sideChosen()
    }

    /**
     * Checks if team A chose side right, all buttons are correctly visible.
     */
    @Test
    fun checkTeamARightSide() {
        UtilityFunctions.clickElement(R.id.teamASideRight)

        sideChosen()
    }

    /**
     * Checks if team B chose side left, all buttons are correctly visible.
     */
    @Test
    fun checkTeamBLeftSide() {
        UtilityFunctions.clickElement(R.id.teamBSideLeft)

        sideChosen()
    }

    /**
     * Checks if team B chose side right, all buttons are correctly visible.
     */
    @Test
    fun checkTeamBRightSide() {
        UtilityFunctions.clickElement(R.id.teamBSideRight)

        sideChosen()
    }

    /**
     * If a team chose a side, the button visibility has to be as defined below.
     */
    private fun sideChosen() {
        // check if all buttons are there as they should be
        // the umpire has to keep track of the player asking order here, thus the serve/accept buttons for both sides
        // are still visible
        UtilityFunctions.isVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamASideLeft)
        UtilityFunctions.isNotVisible(R.id.teamASideRight)
        UtilityFunctions.isNotVisible(R.id.teamBSideLeft)
        UtilityFunctions.isNotVisible(R.id.teamBSideRight)
    }


/////////////////////////////////////// Two chosen Tests //////////////////////////
    /**
     * Checks if after serve and accept was decided the button visibility is correct.
     */
    @Test
    fun teamAChoseFirstThenTeamB() {
        // list all possible choosing combinations
        val chosenList = listOf(
            Pair(R.id.teamAPlayer1Serve, R.id.teamBPlayer1Accept),
            Pair(R.id.teamAPlayer2Serve, R.id.teamBPlayer1Accept),

            Pair(R.id.teamAPlayer1Serve, R.id.teamBPlayer2Accept),
            Pair(R.id.teamAPlayer2Serve, R.id.teamBPlayer2Accept),


            Pair(R.id.teamAPlayer1Accept, R.id.teamBPlayer1Serve),
            Pair(R.id.teamAPlayer2Accept, R.id.teamBPlayer1Serve),

            Pair(R.id.teamAPlayer1Accept, R.id.teamBPlayer2Serve),
            Pair(R.id.teamAPlayer2Accept, R.id.teamBPlayer2Serve),
        )

        // for each combination run it, check if the buttons are visible as they should be, then undo to check the next
        chosenList.forEach{
            UtilityFunctions.clickElement(it.first)
            UtilityFunctions.clickElement(it.second)

            serveAcceptChosenTeamAFirst()
            UtilityFunctions.clickElement(R.id.undo_serve)
        }
    }

    /**
     * Checks if after serve and accept was decided the button visibility is correct.
     */
    @Test
    fun teamBChoseFirstThenTeamA() {
        // list all possible choosing combinations
        val chosenList = listOf(
            Pair(R.id.teamBPlayer1Serve, R.id.teamAPlayer1Accept),
            Pair(R.id.teamBPlayer2Serve, R.id.teamAPlayer1Accept),

            Pair(R.id.teamBPlayer1Serve, R.id.teamAPlayer2Accept),
            Pair(R.id.teamBPlayer2Serve, R.id.teamAPlayer2Accept),


            Pair(R.id.teamBPlayer1Accept, R.id.teamAPlayer1Serve),
            Pair(R.id.teamBPlayer2Accept, R.id.teamAPlayer1Serve),

            Pair(R.id.teamBPlayer1Accept, R.id.teamAPlayer2Serve),
            Pair(R.id.teamBPlayer2Accept, R.id.teamAPlayer2Serve),
        )

        // for each combination run it, check if the buttons are visible as they should be, then undo to check the next
        chosenList.forEach{
            UtilityFunctions.clickElement(it.first)
            UtilityFunctions.clickElement(it.second)

            serveAcceptChosenTeamBFirst()
            UtilityFunctions.clickElement(R.id.undo_serve)
        }
    }

    /**
     * If both teams chose serve and accept and team A chose first, the button visibility has to be as defined below.
     */
    private fun serveAcceptChosenTeamAFirst() {
        // check if all buttons are there as they should be
        // the umpire has to keep track of the player asking order here, thus the serve/accept buttons for both sides
        // are still visible
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamASideLeft)
        UtilityFunctions.isNotVisible(R.id.teamASideRight)
        UtilityFunctions.isVisible(R.id.teamBSideLeft)
        UtilityFunctions.isVisible(R.id.teamBSideRight)
    }

    /**
     * If both teams chose serve and accept and team A chose first, the button visibility has to be as defined below.
     */
    private fun serveAcceptChosenTeamBFirst() {
        // check if all buttons are there as they should be
        // the umpire has to keep track of the player asking order here, thus the serve/accept buttons for both sides
        // are still visible
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamAPlayer2Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer1Accept)

        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Serve)
        UtilityFunctions.isNotVisible(R.id.teamBPlayer2Accept)

        UtilityFunctions.isVisible(R.id.teamASideLeft)
        UtilityFunctions.isVisible(R.id.teamASideRight)
        UtilityFunctions.isNotVisible(R.id.teamBSideLeft)
        UtilityFunctions.isNotVisible(R.id.teamBSideRight)
    }

/////////////////////////////////////// Decision Tests //////////////////////////

    /**
     * Checks if after serve and accept was decided the button visibility is correct.
     */
    @Test
    fun testFinalDecision() {
        // list all possible choosing combinations
        // the first element of each array defines the first button to click, the second element is the second button
        // the third element gives the player on the right side for team A, the fourth element the player on the left side for team B
        // the fifth element gives the player on the left side for team A, the sixth element the player on the right side for team B


        val chosenListA = listOf(

            arrayOf<Any>(R.id.teamAPlayer1Serve, R.id.teamBPlayer1Accept, player1String, player3String, player2String, player4String),
            arrayOf<Any>(R.id.teamAPlayer2Serve, R.id.teamBPlayer1Accept, player2String, player3String, player1String, player4String),

            arrayOf<Any>(R.id.teamAPlayer1Serve, R.id.teamBPlayer2Accept, player1String, player4String, player2String, player3String),
            arrayOf<Any>(R.id.teamAPlayer2Serve, R.id.teamBPlayer2Accept, player2String, player4String, player1String, player3String),

            arrayOf<Any>(R.id.teamAPlayer1Accept, R.id.teamBPlayer1Serve, player1String, player3String, player2String, player4String),
            arrayOf<Any>(R.id.teamAPlayer2Accept, R.id.teamBPlayer1Serve, player2String, player3String, player1String, player4String),

            arrayOf<Any>(R.id.teamAPlayer1Accept, R.id.teamBPlayer2Serve, player1String, player4String, player2String, player3String),
            arrayOf<Any>(R.id.teamAPlayer2Accept, R.id.teamBPlayer2Serve, player2String, player4String, player1String, player3String),
        )

        val chosenListB = listOf(
            arrayOf<Any>(R.id.teamBPlayer1Serve, R.id.teamAPlayer1Accept, player3String, player1String, player4String, player2String),
            arrayOf<Any>(R.id.teamBPlayer2Serve, R.id.teamAPlayer1Accept, player4String, player1String, player3String, player2String),

            arrayOf<Any>(R.id.teamBPlayer1Serve, R.id.teamAPlayer2Accept, player3String, player2String, player4String, player1String),
            arrayOf<Any>(R.id.teamBPlayer2Serve, R.id.teamAPlayer2Accept, player4String, player2String, player3String, player1String),


            arrayOf<Any>(R.id.teamBPlayer1Accept, R.id.teamAPlayer1Serve, player3String, player1String, player4String, player2String),
            arrayOf<Any>(R.id.teamBPlayer2Accept, R.id.teamAPlayer1Serve, player4String, player1String, player3String, player2String),

            arrayOf<Any>(R.id.teamBPlayer1Accept, R.id.teamAPlayer2Serve, player3String, player2String, player4String, player1String),
            arrayOf<Any>(R.id.teamBPlayer2Accept, R.id.teamAPlayer2Serve, player4String, player2String, player3String, player1String),
        )

        // go over all possible choice combinations and then check if side left was chosen by team B
        chosenListA.forEach{
            val chosenSide = R.id.teamBSideLeft
            checkPlayerOrderInMatchViewChooserRight(it, chosenSide)
        }

        // go over all possible choice combinations and then check if side left was chosen by team B
        chosenListA.forEach{
            val chosenSide = R.id.teamBSideRight
            checkPlayerOrderInMatchViewChooserLeft(it, chosenSide)
        }

        // go over all possible choice combinations and then check if side left was chosen by team B
        chosenListB.forEach{
            val chosenSide = R.id.teamASideLeft
            checkPlayerOrderInMatchViewChooserRight(it, chosenSide)
        }

        // go over all possible choice combinations and then check if side left was chosen by team B
        chosenListB.forEach{
            val chosenSide = R.id.teamASideRight
            checkPlayerOrderInMatchViewChooserLeft(it, chosenSide)
        }
    }

    /**
     * Checks if the given player order is also presented as wished in the match view.
     * This function only works if the first chooser is on the right side.
     */
    private fun checkPlayerOrderInMatchViewChooserRight(PlayerOrder: Array<Any>, chosenSide: Int) {
        UtilityFunctions.clickElement(PlayerOrder[0] as Int)
        UtilityFunctions.clickElement(PlayerOrder[1] as Int)
        UtilityFunctions.clickElement(chosenSide)

        UtilityFunctions.hasText(R.id.player_right_even, PlayerOrder[2] as String)
        UtilityFunctions.hasText(R.id.player_left_even, PlayerOrder[3] as String)
        UtilityFunctions.hasText(R.id.player_right_uneven, PlayerOrder[4] as String)
        UtilityFunctions.hasText(R.id.player_left_uneven, PlayerOrder[5] as String)

        UtilityFunctions.clickElement(R.id.undo)
        UtilityFunctions.clickElement(R.id.undo_serve)
    }

    /**
     * Checks if the given player order is also presented as wished in the match view.
     * This function only works if the first chooser is on the left side.
     */
    private fun checkPlayerOrderInMatchViewChooserLeft(PlayerOrder: Array<Any>, chosenSide: Int) {
        UtilityFunctions.clickElement(PlayerOrder[0] as Int)
        UtilityFunctions.clickElement(PlayerOrder[1] as Int)
        UtilityFunctions.clickElement(chosenSide)

        UtilityFunctions.hasText(R.id.player_left_even, PlayerOrder[2] as String)
        UtilityFunctions.hasText(R.id.player_right_even, PlayerOrder[3] as String)
        UtilityFunctions.hasText(R.id.player_left_uneven, PlayerOrder[4] as String)
        UtilityFunctions.hasText(R.id.player_right_uneven, PlayerOrder[5] as String)

        UtilityFunctions.clickElement(R.id.undo)
        UtilityFunctions.clickElement(R.id.undo_serve)
    }
}
