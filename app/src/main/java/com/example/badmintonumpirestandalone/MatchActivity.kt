package com.example.badmintonumpirestandalone

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.badmintonumpirestandalone.model.Match
import com.example.badmintonumpirestandalone.model.SingleMatch
import kotlinx.android.synthetic.main.activity_match.*


class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val match = intent.getSerializableExtra("match")
        if (match is Match) {
            if (match is SingleMatch) {
                announce.text = match.printStartWording(resources.getString(R.string.match_start_wording_single_non_team))

                drawPlayerNamesAndPoints(match)
            } else {
                announce.text = match.printStartWording(resources.getString(R.string.match_start_wording_double_non_team))

                drawPlayerNamesAndPoints(match)
            }

            point_left.setOnClickListener {
                if (match.sets.last().addPointLeft()) {
                    drawPlayerNamesAndPoints(match)
                } else {
                    drawPlayerNamesAndPoints(match)
                    // TODO add proper output, choosing of next set and match win condition
                    announce.text = match.printStartWording("Der Satz wurde gewonnen.")
                }
            }

            point_right.setOnClickListener {
                if (match.sets.last().addPointRight()) {
                    drawPlayerNamesAndPoints(match)
                } else {
                    drawPlayerNamesAndPoints(match)
                    // TODO add proper output, choosing of next set and match win condition
                    announce.text = match.printStartWording("Der Satz wurde gewonnen.")
                }
            }


        }

    }

    private fun drawPlayerNamesAndPoints(match: Match) {
        player_left_even.text = match getPlayer match.currentSet().playerLeftEven
        player_left_uneven.text = match getPlayer match.currentSet().playerLeftUneven
        player_right_even.text = match getPlayer match.currentSet().playerRightEven
        player_right_uneven.text = match getPlayer match.currentSet().playerRightUneven

        val pointText = StringBuilder()
        match.sets.forEach { pointText.append("${it.points.last().pointA} - ${it.points.last().pointB}\n") }
        pointsLabel.text = pointText.toString()

        if (serve_arrow.height != 0) {
            drawArrow(0f, 0f)
        }


    }

    /**
     * The starting coordinates define the player that serves.
     */
    private fun drawArrow(startX: Float, startY: Float) {
        val isStartXLeft = startX == 0f
        val isStartYUpper = startY == 0f
        val height = serve_arrow.height
        val width = serve_arrow.width

        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.RED
        val path = Path()
        path.moveTo(startX, startY)
        path.lineTo(
            if (isStartXLeft) width.toFloat() else 0f,
            if (isStartYUpper) height.toFloat() else 0f
        )
        //Create a new image bitmap and attach a brand new canvas to it
        //Create a new image bitmap and attach a brand new canvas to it
        val tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(tempBitmap)

        tempCanvas.drawPath(path, paint)
        serve_arrow.setImageDrawable(BitmapDrawable(resources, tempBitmap))
    }

}
