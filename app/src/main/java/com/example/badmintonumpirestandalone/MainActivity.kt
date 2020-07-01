package com.example.badmintonumpirestandalone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var single = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        single_double.setOnClickListener {
            player2.isVisible = !single
            player4.isVisible = !single
            single_double.text = if (single) getString(R.string.single) else getString(R.string.double_match)
            single = !single
        }
    }

}
