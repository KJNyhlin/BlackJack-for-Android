package com.example.blackjack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EnterNameActivity : AppCompatActivity() {

    private lateinit var enterNameField : EditText
    private lateinit var enterNamePrompt : TextView
    private lateinit var name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        val button = findViewById<Button>(R.id.enterNameButton)

        enterNamePrompt = findViewById(R.id.enterNamePrompt)
        enterNameField = findViewById(R.id.enterNameField)
        name = enterNameField.text.toString()
        //TODO: Debug print, remove before release
        Log.d("!!!", "Du svarade $name")

        button.setOnClickListener {
            //if (name == "") {
            //    name = "Player"
            //}
            startGame()
        }


    }
    private fun startGame() {
        val intent = Intent(this, GameActivity2::class.java)
        intent.putExtra("playerName", name)
        //intent.putExtra("highestWinnings", highestWinnings)
        startActivity(intent)
    }
}