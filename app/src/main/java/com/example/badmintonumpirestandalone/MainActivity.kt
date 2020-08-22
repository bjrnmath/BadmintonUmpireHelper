package com.example.badmintonumpirestandalone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.badmintonumpirestandalone.model.DoubleMatch
import com.example.badmintonumpirestandalone.model.Match
import com.example.badmintonumpirestandalone.model.SingleMatch
import com.example.badmintonumpirestandalone.model.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var single = true
    private var teamMatch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val masterKey = MasterKey
            .Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            this,
            Utils.PREFERENCESNAME,
            masterKey, // masterKey created above
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val matchString = sharedPreferences.getString(Utils.PREFERENCESMATCHKEY, "")
        val loadMatchObject = Match.fromSerializedString(matchString!!)

        // if there is a matchobject with at least one set, then load it
        // if no set is present, the match was not started and we cannot load it
        if (loadMatchObject != null && loadMatchObject.sets.isNotEmpty()) {
            load_match.isVisible = true
            load_match.text = "${resources.getString(R.string.load_existing_match)}:\n\n" +
                    "${loadMatchObject.printTeamA()} ${resources.getString(R.string.vs)} ${loadMatchObject.printTeamB()}\n" +
                    loadMatchObject.printAllPoints()
            load_match.setOnClickListener {
                val intent = Intent(this, MatchActivity::class.java).apply {
                    putExtra("match", loadMatchObject)
                }
                startActivity(intent)
            }

        }

        Switch_Single_Double.setOnCheckedChangeListener { _, isChecked ->
            single = !isChecked
            player2.isVisible = !single
            player4.isVisible = !single
            Switch_Single_Double.text = if (single) getString(R.string.single) else getString(R.string.double_match)
        }

        Switch_Tournament_Team.setOnCheckedChangeListener { _, isChecked ->
            teamMatch = isChecked
            teamA_name.isVisible = teamMatch
            teamB_name.isVisible = teamMatch
            Switch_Tournament_Team.text = if (isChecked) getString(R.string.team_game) else getString(R.string.tournament_game)
        }

        start_match.setOnClickListener {
            val match = if (single) {
                val teamA = listOf(player1.text.toString())
                val teamB = listOf(player3.text.toString())
                SingleMatch(teamA, teamB, Switch_Tournament_Team.isChecked,
                    teamA_name.text.toString(), teamB_name.text.toString())
            } else {
                val teamA = listOf(player1.text.toString(), player2.text.toString())
                val teamB = listOf(player3.text.toString(), player4.text.toString())
                DoubleMatch(teamA, teamB, Switch_Tournament_Team.isChecked,
                    teamA_name.text.toString(), teamB_name.text.toString())
            }
            val intent = Intent(this, SelectServeActivity::class.java).apply {
                putExtra("match", match)
            }
            startActivity(intent)
        }
    }

}
