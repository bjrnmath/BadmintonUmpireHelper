package com.example.badmintonumpirestandalone

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
                start_match_text.text = match.printStartWording(resources.getString(R.string.match_start_wording_single_non_team))
            } else {
                start_match_text.text = match.printStartWording(resources.getString(R.string.match_start_wording_double_non_team))
            }
        }
    }

}
