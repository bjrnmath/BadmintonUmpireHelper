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

                drawPlayerNames(match)
            } else {
                start_match_text.text = match.printStartWording(resources.getString(R.string.match_start_wording_double_non_team))

                drawPlayerNames(match)
            }
        }
    }

    private fun drawPlayerNames(match: Match) {
        player_left_even.text = match getPlayer match.currentSet().player_left_even
        player_left_uneven.text = match getPlayer match.currentSet().player_left_uneven
        player_right_even.text = match getPlayer match.currentSet().player_right_even
        player_right_uneven.text = match getPlayer match.currentSet().player_right_uneven
    }

}
