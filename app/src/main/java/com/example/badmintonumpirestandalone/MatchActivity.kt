package com.example.badmintonumpirestandalone

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.badmintonumpirestandalone.model.DoubleMatch
import com.example.badmintonumpirestandalone.model.Match
import com.example.badmintonumpirestandalone.model.PlayerIDs
import com.example.badmintonumpirestandalone.model.SingleMatch
import kotlinx.android.synthetic.main.activity_match.*
import kotlin.math.sqrt


class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val match = intent.getSerializableExtra("match")

        if (match is Match) {
            val layout = findViewById<View>(android.R.id.content).rootView
            val vto = layout.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        layout.viewTreeObserver
                                .removeOnGlobalLayoutListener(this)
                    } else {
                        layout.viewTreeObserver
                                .removeGlobalOnLayoutListener(this)
                    }
                    if (match is SingleMatch) {
                        drawPlayerNamesAndPoints(match)
                        announce.text = match.printStartWording(resources.getString(R.string.match_start_wording_single_non_team))
                    } else {
                        drawPlayerNamesAndPoints(match)
                        announce.text = match.printStartWording(resources.getString(R.string.match_start_wording_double_non_team))
                    }
                }
            })

            player_teamA_static.text = match.printTeamA()
            player_teamB_static.text = match.printTeamB()

            point_left.setOnClickListener {
                val isSetNotFinished = match.addPointLeft()
                if (isSetNotFinished) {
                    drawPlayerNamesAndPoints(match)
                } else {
                    if (match.nextSetExists()) {
                        startNextSetSequence(match)
                    } else {
                        drawPlayerNamesAndPoints(match)
                        showEndGame(match)
                    }
                }
            }

            point_right.setOnClickListener {
                val isSetNotFinished = match.addPointRight()
                if (isSetNotFinished) {
                    drawPlayerNamesAndPoints(match)
                } else {
                    if (match.nextSetExists()) {
                        startNextSetSequence(match)
                    } else {
                        drawPlayerNamesAndPoints(match)
                        showEndGame(match)
                    }
                }
            }

            undo.setOnClickListener {
                match.undo()
                drawPlayerNamesAndPoints(match)
                // in case the match already ended, we can resume it here
                point_left.isClickable = true
                point_right.isClickable = true
                point_left.alpha = 1f
                point_right.alpha = 1f
            }
        }
    }

    private fun startNextSetSequence(match: Match) {
        // for single matches it is clear who serves and accepts
        drawPlayerNamesAndPoints(match)
        val setStartAnnounce = if (match.sets.size == 1) R.string.first_set_end else R.string.second_set_end
        announce.text = match.nextSetAnnounce(resources.getString(setStartAnnounce), resources.getString(R.string.and))
        if (match is DoubleMatch) {
            val winner = match.getSetWinner()
            val loser = match.getSetLoser()
            var serve = PlayerIDs.UNDEF
            var accept = PlayerIDs.UNDEF
            next_set_selection.isVisible = true
            serve_between_sets_option1.isVisible = true
            serve_between_sets_option2.isVisible = true
            serve_between_sets.isVisible = true
            accept_between_sets_option1.isVisible = true
            accept_between_sets_option2.isVisible = true
            accept_between_sets.isVisible = true
            point_left.isVisible = false
            point_right.isVisible = false

            val startNext = {
                if (serve != PlayerIDs.UNDEF && accept != PlayerIDs.UNDEF) {
                    match.startNextSet(serve, accept)
                    next_set_selection.isVisible = false
                    point_left.isVisible = true
                    point_right.isVisible = true
                    drawPlayerNamesAndPoints(match)
                    announce.text = if (match.sets.size == 2)
                        resources.getString(R.string.second_set_start)
                    else
                        resources.getString(R.string.third_set_start)
                }
            }

            serve_between_sets_option1.text = match getPlayerNameFrom winner.first
            serve_between_sets_option1.setOnClickListener {
                serve_between_sets_option1.isVisible = false
                serve_between_sets_option2.isVisible = false
                serve_between_sets.isVisible = false
                serve = winner.first
                startNext()
            }
            serve_between_sets_option2.text = match getPlayerNameFrom winner.second
            serve_between_sets_option2.setOnClickListener {
                serve_between_sets_option1.isVisible = false
                serve_between_sets_option2.isVisible = false
                serve_between_sets.isVisible = false
                serve = winner.second
                startNext()
            }

            accept_between_sets_option1.text = match getPlayerNameFrom loser.first
            accept_between_sets_option1.setOnClickListener {
                accept_between_sets_option1.isVisible = false
                accept_between_sets_option2.isVisible = false
                accept_between_sets.isVisible = false
                accept = loser.first
                startNext()
            }
            accept_between_sets_option2.text = match getPlayerNameFrom loser.second
            accept_between_sets_option2.setOnClickListener {
                accept_between_sets_option1.isVisible = false
                accept_between_sets_option2.isVisible = false
                accept_between_sets.isVisible = false
                accept = loser.second
                startNext()
            }
        } else {
            match.startNextSet(match.getSetWinner().first, match.getSetLoser().first)
            drawPlayerNamesAndPoints(match)
        }
    }

    private fun showEndGame(match: Match) {
        announce.text = match.printWinWording(
            resources.getString(R.string.win_wording_non_team),
            resources.getString(R.string.and)
        )
        point_left.isClickable = false
        point_right.isClickable = false
        point_left.alpha = 0.5f
        point_right.alpha = 0.5f
    }

    private fun drawPlayerNamesAndPoints(match: Match) {
        val currentSet = match.currentSet()
        player_left_even.text = match getPlayerNameFrom currentSet.playerLeftEven
        player_left_uneven.text = match getPlayerNameFrom currentSet.playerLeftUneven
        player_right_even.text = match getPlayerNameFrom currentSet.playerRightEven
        player_right_uneven.text = match getPlayerNameFrom currentSet.playerRightUneven

        val pointText = StringBuilder()
        match.sets.forEach { pointText.append("${it.points.last().pointA} - ${it.points.last().pointB}\n") }
        pointsLabel.text = pointText.toString()

        announce.text = buildAnnounceText(match)

        if (serve_arrow.height > 0) {
            val servePosX = if (currentSet.isServeLeft()) 0f else serve_arrow.width.toFloat()

            val servePosY = if (currentSet.isServeLeft() && currentSet.getCurrentPointsLeft() % 2 != 0)
                0f
            else
                if (currentSet.isServeLeft() && currentSet.getCurrentPointsLeft() % 2 == 0)
                    serve_arrow.height.toFloat()
                else
                    if (!currentSet.isServeLeft() && currentSet.getCurrentPointsRight() % 2 == 0)
                        0f
                    else
                        serve_arrow.height.toFloat()
            drawArrow(servePosX, servePosY)
        }



    }

    private fun buildAnnounceText(match: Match): String {
        val announceBuilder = StringBuilder()
        if (match.currentSet().serveChanged()) {
            announceBuilder.append("${resources.getString(R.string.serve_change)} ")
        }
        announceBuilder.append(match.getCurrentPointsString(
            resources.getString(R.string.both),
            resources.getString(R.string.setpoint),
            resources.getString(R.string.matchpoint)
        ))
        if (match.currentSet().isBreak()) {
            announceBuilder.append(". ${resources.getString(R.string.break_set_mid)}")
        }

        return announceBuilder.toString()
    }

    /**
     * The starting coordinates define the player that serves.
     */
    private fun drawArrow(startXParam: Float, startYParam: Float) {
        val isStartXLeft = startXParam == 0f
        val isStartYUpper = startYParam == 0f
        val height = serve_arrow.height
        val width = serve_arrow.width

        val edgeCorrectionX = 3f
        val edgeCorrectionY = 10f
        val startX = if (isStartXLeft) edgeCorrectionX else startXParam - edgeCorrectionX
        val startY = if (isStartYUpper) edgeCorrectionY else startYParam - edgeCorrectionY
        val endX = if (isStartXLeft) width.toFloat() - edgeCorrectionX else edgeCorrectionX
        val endY = if (isStartYUpper) height.toFloat() - edgeCorrectionY else edgeCorrectionY



        //Create a new image bitmap and attach a brand new canvas to it
        //Create a new image bitmap and attach a brand new canvas to it
        val tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(tempBitmap)
        drawArrow(tempCanvas, startX, startY, endX, endY)

        serve_arrow.setImageDrawable(BitmapDrawable(resources, tempBitmap))
    }

    private fun drawArrow(
        canvas: Canvas,
        x0: Float,
        y0: Float,
        x1: Float,
        y1: Float
    ) {
        val headPaint = Paint()
        headPaint.style = Paint.Style.STROKE
        headPaint.strokeWidth = 1f
        headPaint.color = Color.LTGRAY
        headPaint.isAntiAlias = true
        headPaint.style = Paint.Style.FILL


        val deltaX = x1 - x0
        val deltaY = y1 - y0
        val distance = sqrt(deltaX * deltaX + (deltaY * deltaY).toDouble())
        val frac = (1 / (distance / 10)).toFloat()

        val pointX1 = x0 + ((1 - frac) * deltaX + frac * deltaY)
        val pointY1 = y0 + ((1 - frac) * deltaY - frac * deltaX)

        val pointX3 = x0 + ((1 - frac) * deltaX - frac * deltaY)
        val pointY3 = y0 + ((1 - frac) * deltaY + frac * deltaX)

        val arrowHead = Path()
        arrowHead.fillType = Path.FillType.EVEN_ODD

        arrowHead.moveTo(pointX1, pointY1)
        arrowHead.lineTo(x1, y1)
        arrowHead.lineTo(pointX3, pointY3)
        arrowHead.lineTo(pointX1, pointY1)
        arrowHead.close()


        val linePaint = Paint()
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 3f
        linePaint.color = Color.LTGRAY
        linePaint.isAntiAlias = true

        val arrowLine = Path()
        arrowLine.moveTo(x0, y0)
        arrowLine.lineTo((pointX1 + pointX3) / 2, (pointY1 + pointY3) / 2)


        canvas.drawPath(arrowHead, headPaint)
        canvas.drawPath(arrowLine, linePaint)
    }

}
