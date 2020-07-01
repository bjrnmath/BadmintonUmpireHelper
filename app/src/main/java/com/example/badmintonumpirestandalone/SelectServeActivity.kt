package com.example.badmintonumpirestandalone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.badmintonumpirestandalone.model.Match
import com.example.badmintonumpirestandalone.model.SingleMatch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_serve.*

class SelectServeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serve)
        val match = intent.getSerializableExtra("match")
        if (match is Match) {
            team_a.text = match.printTeamA()
            team_b.text = match.printTeamB()
        }
    }

}
