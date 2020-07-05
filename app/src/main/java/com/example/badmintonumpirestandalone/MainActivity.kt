package com.example.badmintonumpirestandalone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.badmintonumpirestandalone.model.DoubleMatch
import com.example.badmintonumpirestandalone.model.SingleMatch
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var single = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val files: Array<File> = filesDir.listFiles()
//        if (files.isEmpty()) {
//
//        }
//        vsText.text = files.contentToString()

        single_double.setOnClickListener {
            single = !single
            player2.isVisible = !single
            player4.isVisible = !single
            single_double.text = if (!single) getString(R.string.single) else getString(R.string.double_match)
        }

        start_match.setOnClickListener {
            val match = if (single) {
                val teamA = listOf(player1.text.toString())
                val teamB = listOf(player3.text.toString())
                SingleMatch(teamA, teamB)
            } else {
                val teamA = listOf(player1.text.toString(), player2.text.toString())
                val teamB = listOf(player3.text.toString(), player4.text.toString())
                DoubleMatch(teamA, teamB)
            }
            val intent = Intent(this, SelectServeActivity::class.java).apply {
                putExtra("match", match)
            }
            startActivity(intent)
        }
    }

}
