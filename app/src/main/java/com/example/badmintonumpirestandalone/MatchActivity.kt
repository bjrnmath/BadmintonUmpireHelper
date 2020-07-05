package com.example.badmintonumpirestandalone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.badmintonumpirestandalone.model.DoubleMatch
import com.example.badmintonumpirestandalone.model.Match
import com.example.badmintonumpirestandalone.model.SingleMatch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_match.*
import java.io.File

class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val match = intent.getSerializableExtra("match")
        if (match is Match) {
            if (match is SingleMatch) {
                start_match_text.text = match.printStartWording(resources.getString(R.string.match_start_wording_single_non_team))
            } else {
                start_match_text.text = match.printStartWording(resources.getString(R.string.match_start_wording_double_non_team))
            }
        }
    }

}
