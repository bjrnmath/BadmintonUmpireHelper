package com.example.badmintonumpirestandalone

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.badmintonumpirestandalone.model.*
import kotlinx.android.synthetic.main.activity_serve.*
import java.util.*


// TODO do some refactoring on this file

enum class Side {
    LEFT,
    RIGHT,
    UNDEF
}

class SelectServeActivity : AppCompatActivity() {

    private lateinit var selectTextViewsSingle: List<TextView>
    private lateinit var selectTextViewsDouble: List<TextView>
    private lateinit var selectButtonsSingle: List<Button>
    private lateinit var selectButtonsDouble: List<Button>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serve)

        selectTextViewsSingle = listOf<TextView>(
            serveTeamAPlayer1,
            serveTeamBPlayer1,
            teamASide,
            teamBSide)

        selectTextViewsDouble = listOf<TextView>(
            serveTeamAPlayer2,
            serveTeamBPlayer2)

        selectButtonsSingle = listOf<Button>(
            teamAPlayer1Serve,
            teamAPlayer1Accept,
            teamASideLeft,
            teamASideRight,

            teamBPlayer1Serve,
            teamBPlayer1Accept,
            teamBSideLeft,
            teamBSideRight
        )

        selectButtonsDouble = listOf<Button>(
            teamAPlayer2Serve,
            teamAPlayer2Accept,

            teamBPlayer2Serve,
            teamBPlayer2Accept
        )

        val match = intent.getSerializableExtra("match")
        if (match is Match) {
            printPlainVsHeader(match)

            randomize.setOnClickListener {
                // this should be a sufficiently random initialization
                randomize.text = if (Random(System.currentTimeMillis()).nextBoolean()) {
                    resources.getString(R.string.head)
                } else {
                    resources.getString(R.string.tails)
                }
                randomize.isClickable = false
                showSelectSingle(match)
                if (match is DoubleMatch) {
                    showSelectDouble(match)
                }
            }

            if (match is SingleMatch) {
                setSelectOnClickListenerSingle(match)
            } else {
                setSelectOnClickListenerDouble(match)
            }
        }
    }

    private fun printPlainVsHeader(match: Match) {
        vsHeader.text = "${match.printTeamA()} vs. ${match.printTeamB()}"
    }

    private fun setSelectOnClickListenerSingle(match: Match) {
        var serve = PlayerIDs.UNDEF
        var accept = PlayerIDs.UNDEF
        var sideChoiceTeamA = Side.UNDEF

        undo_serve.setOnClickListener {
            // if nothing was done by now, we can go fully back to name choice
            if (sideChoiceTeamA == Side.UNDEF && serve == PlayerIDs.UNDEF && accept == PlayerIDs.UNDEF) {
                finish()
            }
            sideChoiceTeamA = Side.UNDEF
            serve = PlayerIDs.UNDEF
            accept = PlayerIDs.UNDEF
            checkReady(serve, accept, sideChoiceTeamA, match) // for correct header printing
            makeSingleVisible()
        }

        // serve for both sides
        teamAPlayer1Serve.setOnClickListener {
            serve = PlayerIDs.TEAMAPLAYER1
            accept = PlayerIDs.TEAMBPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAInvisible()
            makeTeamBServeAcceptInvisible()
        }
        teamBPlayer1Serve.setOnClickListener {
            serve = PlayerIDs.TEAMBPLAYER1
            accept = PlayerIDs.TEAMAPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamBInvisible()
            makeTeamAServeAcceptInvisible()
        }

        // accept for both sides
        teamAPlayer1Accept.setOnClickListener {
            serve = PlayerIDs.TEAMBPLAYER1
            accept = PlayerIDs.TEAMAPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAInvisible()
            makeTeamBServeAcceptInvisible()
        }
        teamBPlayer1Accept.setOnClickListener {
            serve = PlayerIDs.TEAMAPLAYER1
            accept = PlayerIDs.TEAMBPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamBInvisible()
            makeTeamAServeAcceptInvisible()
        }

        // right side for both sides
        teamASideRight.setOnClickListener {
            sideChoiceTeamA = Side.RIGHT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAInvisible()
            makeTeamBSideInvisible()
        }
        teamBSideRight.setOnClickListener {
            sideChoiceTeamA = Side.LEFT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamBInvisible()
            makeTeamASideInvisible()
        }

        // left side for both sides
        teamASideLeft.setOnClickListener {
            sideChoiceTeamA = Side.LEFT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAInvisible()
            makeTeamBSideInvisible()
        }
        teamBSideLeft.setOnClickListener {
            sideChoiceTeamA = Side.RIGHT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamBInvisible()
            makeTeamASideInvisible()
        }

    }

    private fun setSelectOnClickListenerDouble(match: Match) {
        var serve = PlayerIDs.UNDEF
        var accept = PlayerIDs.UNDEF
        var sideChoiceTeamA = Side.UNDEF

        undo_serve.setOnClickListener {
            // if nothing was done by now, we can go fully back to name choice
            if (sideChoiceTeamA == Side.UNDEF && serve == PlayerIDs.UNDEF && accept == PlayerIDs.UNDEF) {
                finish()
            }
            sideChoiceTeamA = Side.UNDEF
            serve = PlayerIDs.UNDEF
            accept = PlayerIDs.UNDEF
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeSingleVisible()
            makeDoubleVisible()
        }

        // serve player 1 for both sides
        teamAPlayer1Serve.setOnClickListener {
            serve = PlayerIDs.TEAMAPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAServeAcceptInvisible()
            makeTeamBServeInvisible()
        }
        teamBPlayer1Serve.setOnClickListener {
            serve = PlayerIDs.TEAMBPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAServeInvisible()
            makeTeamBServeAcceptInvisible()
        }

        // serve player 2 for both sides
        teamAPlayer2Serve.setOnClickListener {
            serve = PlayerIDs.TEAMAPLAYER2
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAServeAcceptInvisible()
            makeTeamBServeInvisible()
        }
        teamBPlayer2Serve.setOnClickListener {
            serve = PlayerIDs.TEAMBPLAYER2
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAServeInvisible()
            makeTeamBServeAcceptInvisible()
        }

        // accept player 1 for both sides
        teamAPlayer1Accept.setOnClickListener {
            accept = PlayerIDs.TEAMAPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAServeAcceptInvisible()
            makeTeamBAcceptInvisible()
        }
        teamBPlayer1Accept.setOnClickListener {
            accept = PlayerIDs.TEAMBPLAYER1
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAAcceptInvisible()
            makeTeamBServeAcceptInvisible()
        }

        // accept player 2 for both sides
        teamAPlayer2Accept.setOnClickListener {
            accept = PlayerIDs.TEAMAPLAYER2
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAServeAcceptInvisible()
            makeTeamBAcceptInvisible()
        }
        teamBPlayer2Accept.setOnClickListener {
            accept = PlayerIDs.TEAMBPLAYER2
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamAAcceptInvisible()
            makeTeamBServeAcceptInvisible()
        }

        // right side for both sides
        teamASideRight.setOnClickListener {
            sideChoiceTeamA = Side.RIGHT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamASideInvisible()
            makeTeamBSideInvisible()
        }
        teamBSideRight.setOnClickListener {
            sideChoiceTeamA = Side.LEFT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamASideInvisible()
            makeTeamBSideInvisible()
        }

        // left side for both sides
        teamASideLeft.setOnClickListener {
            sideChoiceTeamA = Side.LEFT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamASideInvisible()
            makeTeamBSideInvisible()
        }
        teamBSideLeft.setOnClickListener {
            sideChoiceTeamA = Side.RIGHT
            checkReady(serve, accept, sideChoiceTeamA, match)
            makeTeamASideInvisible()
            makeTeamBSideInvisible()
        }
    }

    override fun onBackPressed() {
        undo_serve.callOnClick()
    }

    private fun makeSingleVisible() {
        selectButtonsSingle.forEach { it.visibility = Button.VISIBLE }
        selectTextViewsSingle.forEach { it.visibility = TextView.VISIBLE }
    }

    private fun makeDoubleVisible() {
        selectButtonsDouble.forEach { it.visibility = Button.VISIBLE }
        selectTextViewsDouble.forEach { it.visibility = TextView.VISIBLE }
    }

    private fun checkReady(serve: PlayerIDs, accept: PlayerIDs, sideTeamA: Side, match: Match) {
        var serveText = ""
        if (serve != PlayerIDs.UNDEF) {
            serveText += "\n${resources.getString(R.string.takes_serve)}: ${match getPlayerNameFrom serve}"
        }

        if (accept != PlayerIDs.UNDEF) {
            serveText += "\n${resources.getString(R.string.takes_accept)}: ${match getPlayerNameFrom accept}"
        }

        if (sideTeamA != Side.UNDEF) {
            serveText += if (sideTeamA == Side.RIGHT) {
                "\n${resources.getString(R.string.Takes_left)}: ${match.printTeamB()}" +
                        "\n${resources.getString(R.string.Takes_right)}: ${match.printTeamA()}"

            } else {
                "\n${resources.getString(R.string.Takes_left)}: ${match.printTeamA()}" +
                        "\n${resources.getString(R.string.Takes_right)}: ${match.printTeamB()}"
            }
        }
        status_header.text = serveText

        if (serve != PlayerIDs.UNDEF && accept != PlayerIDs.UNDEF && sideTeamA != Side.UNDEF) {
            match.sets.add(
                MatchSet(match, serve, accept, sideTeamA == Side.RIGHT)
            )
            val intent = Intent(this, MatchActivity::class.java).apply {
                putExtra("match", match)
            }
            startActivity(intent)
        }
    }

    private fun makeTeamAInvisible() {
        teamAPlayer1Serve.visibility = Button.INVISIBLE
        teamAPlayer1Accept.visibility = Button.INVISIBLE
        serveTeamAPlayer1.visibility = TextView.INVISIBLE

        teamAPlayer2Serve.visibility = Button.INVISIBLE
        teamAPlayer2Accept.visibility = Button.INVISIBLE
        serveTeamAPlayer2.visibility = TextView.INVISIBLE

        teamASide.visibility = TextView.INVISIBLE
        teamASideLeft.visibility = Button.INVISIBLE
        teamASideRight.visibility = Button.INVISIBLE
    }

    private fun makeTeamAServeAcceptInvisible() {
        // if everything is still visible we are in a double match and A made the choice to either serve or
        // accept, so it cannot make any other choice, otherwise we just make the serve/accept choice invisible
        if (teamAPlayer1Serve.isVisible && teamAPlayer1Accept.isVisible &&
            teamAPlayer2Serve.isVisible && teamAPlayer2Accept.isVisible) {
            makeTeamAInvisible()
        } else {
            teamAPlayer1Serve.visibility = Button.INVISIBLE
            teamAPlayer1Accept.visibility = Button.INVISIBLE
            serveTeamAPlayer1.visibility = TextView.INVISIBLE

            teamAPlayer2Serve.visibility = Button.INVISIBLE
            teamAPlayer2Accept.visibility = Button.INVISIBLE
            serveTeamAPlayer2.visibility = TextView.INVISIBLE
        }
    }

    private fun makeTeamAServeInvisible() {
        teamAPlayer1Serve.visibility = Button.INVISIBLE
        teamAPlayer2Serve.visibility = Button.INVISIBLE
    }

    private fun makeTeamAAcceptInvisible() {
        teamAPlayer1Accept.visibility = Button.INVISIBLE
        teamAPlayer2Accept.visibility = Button.INVISIBLE
    }

    private fun makeTeamASideInvisible() {
        teamASide.visibility = TextView.INVISIBLE
        teamASideLeft.visibility = Button.INVISIBLE
        teamASideRight.visibility = Button.INVISIBLE
    }

    private fun makeTeamBServeAcceptInvisible() {
        // if everything is still visible we are in a double match and B made the choice to either serve or
        // accept, so it cannot make any other choice, otherwise we just make the serve/accept choice invisible
        if (teamBPlayer1Serve.isVisible && teamBPlayer1Accept.isVisible &&
            teamBPlayer2Serve.isVisible && teamBPlayer2Accept.isVisible) {
            makeTeamBInvisible()
        } else {
            teamBPlayer1Serve.visibility = Button.INVISIBLE
            teamBPlayer1Accept.visibility = Button.INVISIBLE
            serveTeamBPlayer1.visibility = TextView.INVISIBLE

            teamBPlayer2Serve.visibility = Button.INVISIBLE
            teamBPlayer2Accept.visibility = Button.INVISIBLE
            serveTeamBPlayer2.visibility = TextView.INVISIBLE
        }
    }

    private fun makeTeamBServeInvisible() {
        teamBPlayer1Serve.visibility = Button.INVISIBLE
        teamBPlayer2Serve.visibility = Button.INVISIBLE

    }

    private fun makeTeamBAcceptInvisible() {
        teamBPlayer1Accept.visibility = Button.INVISIBLE
        teamBPlayer2Accept.visibility = Button.INVISIBLE
    }

    private fun makeTeamBSideInvisible() {
        teamBSide.visibility = TextView.INVISIBLE
        teamBSideLeft.visibility = Button.INVISIBLE
        teamBSideRight.visibility = Button.INVISIBLE
    }

    private fun makeTeamBInvisible() {
        teamBPlayer1Serve.visibility = Button.INVISIBLE
        teamBPlayer1Accept.visibility = Button.INVISIBLE
        serveTeamBPlayer1.visibility = TextView.INVISIBLE

        teamBPlayer2Serve.visibility = Button.INVISIBLE
        teamBPlayer2Accept.visibility = Button.INVISIBLE
        serveTeamBPlayer2.visibility = TextView.INVISIBLE

        teamBSide.visibility = TextView.INVISIBLE
        teamBSideLeft.visibility = Button.INVISIBLE
        teamBSideRight.visibility = Button.INVISIBLE
    }


    /**
     * Sets the visibility and text for the select buttons and text views.
     */
    private fun showSelectSingle(match: Match) {
        serveTeamAPlayer1.text = match.playerTeamA[0]
        serveTeamBPlayer1.text = match.playerTeamB[0]
        teamASide.text = match.printTeamA()
        teamBSide.text = match.printTeamB()
        selectTextViewsSingle.forEach {
            it.visibility = TextView.VISIBLE
        }
        selectButtonsSingle.forEach { it.visibility = Button.VISIBLE }
    }

    /**
     * Sets the visibility and text for the choose buttons and removes the winner buttons.
     */
    private fun showSelectDouble(match: Match) {
        serveTeamAPlayer2.text = match.playerTeamA[1]
        serveTeamBPlayer2.text = match.playerTeamB[1]
        selectTextViewsDouble.forEach {
            it.visibility = TextView.VISIBLE
        }
        selectButtonsDouble.forEach { it.visibility = Button.VISIBLE }
    }

}
