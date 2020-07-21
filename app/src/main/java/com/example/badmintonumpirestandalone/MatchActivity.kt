package com.example.badmintonumpirestandalone

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
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
                        announce.text = match.printStartWording(resources.getString(R.string.match_start_wording_single_non_team))

                        drawPlayerNamesAndPoints(match)
                    } else {
                        announce.text = match.printStartWording(resources.getString(R.string.match_start_wording_double_non_team))

                        drawPlayerNamesAndPoints(match)
                    }
                }
            })
            point_left.setOnClickListener {
                if (match.sets.last().addPointLeft()) {
                    drawPlayerNamesAndPoints(match)
                } else {
                    announce.text = match.printStartWording("Der Satz wurde gewonnen.")
                    if (match.nextSetExists()) {
                        // TODO start question who serves and who accepts based on result
                        match.startNextSet(PlayerIDs.TEAMBPLAYER1, PlayerIDs.TEAMAPLAYER1)
                    } else {
                        announce.text = match.printStartWording("Das Spiel wurde gewonnen.")
                    }
                    drawPlayerNamesAndPoints(match)
                }
            }

            point_right.setOnClickListener {
                if (match.sets.last().addPointRight()) {
                    drawPlayerNamesAndPoints(match)
                } else {
                    announce.text = match.printStartWording("Der Satz wurde gewonnen.")
                    if (match.nextSetExists()) {
                        // TODO start question who serves and who accepts based on result
                        match.startNextSet(PlayerIDs.TEAMBPLAYER1, PlayerIDs.TEAMAPLAYER1)
                    } else {
                        announce.text = match.printStartWording("Das Spiel wurde gewonnen.")
                    }
                    drawPlayerNamesAndPoints(match)
                }
            }
        }
    }

    private fun drawPlayerNamesAndPoints(match: Match) {
        val currentSet = match.currentSet()
        player_left_even.text = match getPlayerName currentSet.playerLeftEven
        player_left_uneven.text = match getPlayerName currentSet.playerLeftUneven
        player_right_even.text = match getPlayerName currentSet.playerRightEven
        player_right_uneven.text = match getPlayerName currentSet.playerRightUneven

        val pointText = StringBuilder()
        match.sets.forEach { pointText.append("${it.points.last().pointA} - ${it.points.last().pointB}\n") }
        pointsLabel.text = pointText.toString()

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
