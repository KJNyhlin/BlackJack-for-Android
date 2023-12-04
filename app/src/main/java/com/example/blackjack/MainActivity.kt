package com.example.blackjack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //private var highestWinnings = 0
    lateinit var titleView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleView = findViewById(R.id.titleView)
        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            toEnterNameActivity()
        }



    }

    private fun toEnterNameActivity() {
        //TODO: ändra så att den går till enterNameActivity
        val intent = Intent(this, GameActivity2::class.java)
        //intent.putExtra("highestWinnings", highestWinnings)
        startActivity(intent)
    }
}