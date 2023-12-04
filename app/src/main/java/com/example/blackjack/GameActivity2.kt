package com.example.blackjack

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity2 : AppCompatActivity() {

    private lateinit var standButton : Button
    private lateinit var hitButton: Button
    private lateinit var cashTextView: TextView
    private var cash = 100

    var cardDeck = listOf<Card>(
        Card(1, "clubs"),
        Card(2, "clubs"),
        Card(3, "clubs"),
        Card(4, "clubs"),
        Card(5, "clubs"),
        Card(6, "clubs"),
        Card(7, "clubs"),
        Card(8, "clubs"),
        Card(9, "clubs"),
        Card(10, "clubs"),
        Card(11, "clubs"),
        Card(12, "clubs"),
        Card(1, "diamonds"),
        Card(2, "diamonds"),
        Card(3, "diamonds"),
        Card(4, "diamonds"),
        Card(5, "diamonds"),
        Card(6, "diamonds"),
        Card(7, "diamonds"),
        Card(8, "diamonds"),
        Card(9, "diamonds"),
        Card(10, "diamonds"),
        Card(11, "diamonds"),
        Card(12, "diamonds"),
        Card(1, "hearts"),
        Card(2, "hearts"),
        Card(3, "hearts"),
        Card(4, "hearts"),
        Card(5, "hearts"),
        Card(6, "hearts"),
        Card(7, "hearts"),
        Card(8, "hearts"),
        Card(9, "hearts"),
        Card(10, "hearts"),
        Card(11, "hearts"),
        Card(12, "hearts"),
        Card(1, "spades"),
        Card(2, "spades"),
        Card(3, "spades"),
        Card(4, "spades"),
        Card(5, "spades"),
        Card(6, "spades"),
        Card(7, "spades"),
        Card(8, "spades"),
        Card(9, "spades"),
        Card(10, "spades"),
        Card(11, "spades"),
        Card(12, "spades"),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)

        val standButton = findViewById<Button>(R.id.standButton)
        val hitButton = findViewById<Button>(R.id.hitButton)

        var blackjack = false
        var playerScore = 0
        var dealerScore = 0
        cashTextView = findViewById(R.id.cashTextView)
        cashTextView.text = "Cash: $cash"




        //standButton.setOnClickListener {
        //    stand()
        //}
//
        //hitButton.setOnClickListener {
        //    hit()
        //}

    }

    fun stand() {
        //TODO: write code
    }

    fun hit() {
        //TODO: write code
    }

}