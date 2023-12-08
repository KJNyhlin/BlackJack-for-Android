package com.example.blackjack

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.view.View
import android.view.View.*
import androidx.core.content.res.ResourcesCompat

class GameActivity2 : AppCompatActivity() {

    private lateinit var standButton: Button
    private lateinit var hitButton: Button
    private lateinit var playAgainButton: Button
    private lateinit var endGameButton: Button
    private lateinit var cashTextView: TextView
    private lateinit var dealerTotalTextView: TextView
    private lateinit var playerTotalTextView: TextView
    private lateinit var bustOrBlackjackText: TextView
    private lateinit var whoWinsText: TextView
    private lateinit var players1stCardImage: ImageView
    private lateinit var players2ndCardImage: ImageView
    private lateinit var players3rdCardImage: ImageView
    private lateinit var players4thCardImage: ImageView
    private lateinit var players5thCardImage: ImageView
    private lateinit var players6thCardImage: ImageView
    private lateinit var players7thCardImage: ImageView
    private lateinit var players8thCardImage: ImageView
    private lateinit var players9thCardImage: ImageView
    private lateinit var dealers1stCardImage: ImageView
    private lateinit var dealers2ndCardImage: ImageView
    private lateinit var dealers3rdCardImage: ImageView
    private lateinit var dealers4thCardImage: ImageView
    private lateinit var dealers5thCardImage: ImageView
    private lateinit var dealers6thCardImage: ImageView
    private lateinit var dealers7thCardImage: ImageView
    private lateinit var cardSound: MediaPlayer
    private lateinit var playBustSound: MediaPlayer
    private lateinit var winnerSound: MediaPlayer

    private var cash = 210

    private var blackJack = false
    private var specialBlackJack = false
    private val blackJackBonus = 10
    private val specialBlackJackBonus = 10
    private var playerTotal = 0
    private var dealerTotal = 0
    private var playerWins = false
    private var dealerWins = false
    private var draw = false

    private var cardDeck = mutableListOf<Card>(
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
        Card(13, "clubs"),
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
        Card(13, "diamonds"),
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
        Card(13, "hearts"),
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
        Card(13, "spades")
    )
    private var usedCards = mutableListOf<Card>()
    private var playersHand = mutableListOf<Card>()
    private var dealersHand = mutableListOf<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)

        standButton = findViewById(R.id.standButton)
        hitButton = findViewById(R.id.hitButton)


        cashTextView = findViewById(R.id.cashTextView)
        cashTextView.text = "Cash: $cash"
        bustOrBlackjackText = findViewById(R.id.bustOrBlackjackText)
        whoWinsText = findViewById(R.id.whoWinsText)
        dealerTotalTextView = findViewById(R.id.dealerTotalTextView)
        playerTotalTextView = findViewById(R.id.playerTotalTextView)
        players1stCardImage = findViewById(R.id.players1stCardImage)
        players2ndCardImage = findViewById(R.id.players2ndCardImage)
        players3rdCardImage = findViewById(R.id.players3rdCardImage)
        players4thCardImage = findViewById(R.id.players4thCardImage)
        players5thCardImage = findViewById(R.id.players5thCardImage)
        players6thCardImage = findViewById(R.id.players6thCardImage)
        players7thCardImage = findViewById(R.id.players7thCardImage)
        players8thCardImage = findViewById(R.id.players8thCardImage)
        players9thCardImage = findViewById(R.id.players9thCardImage)
        dealers1stCardImage = findViewById(R.id.dealer1stCardImage)
        dealers2ndCardImage = findViewById(R.id.dealer2ndCardImage)
        dealers3rdCardImage = findViewById(R.id.dealer3rdCardImage)
        dealers4thCardImage = findViewById(R.id.dealer4thCardImage)
        dealers5thCardImage = findViewById(R.id.dealer5thCardImage)
        dealers6thCardImage = findViewById(R.id.dealer6thCardImage)
        dealers7thCardImage = findViewById(R.id.dealer7thCardImage)



        playAgainButton = findViewById(R.id.playAgainButton)


        endGameButton = findViewById(R.id.endGameButton)


        cardSound = MediaPlayer.create(this, R.raw.cardsound)
        playBustSound = MediaPlayer.create(this, R.raw.bust_drum)
        winnerSound = MediaPlayer.create(this, R.raw.ping)


        initializeRound()

        standButton.setOnClickListener {
            stand()
        }
        hitButton.setOnClickListener {
            dealCardToPlayer()
            playerTotal = calculateTotal(true)
        }

        playAgainButton.setOnClickListener {
            initializeRound()
        }
        endGameButton.setOnClickListener {
            finish()
        }


    }

    private fun calculateTotal(player: Boolean): Int {
        var total = 0
        if (player) {
            for (card: Card in playersHand) {
                total += calculateCardValue(card, total)
            }
        } else {
            for (card: Card in dealersHand) {
                total += calculateCardValue(card, total)
            }
        }
        return total
    }

    private fun initializeRound() {
        playAgainButton.isEnabled = false
        playAgainButton.makeInvisible()
        endGameButton.isEnabled = false
        endGameButton.makeInvisible()
        standButton.isEnabled = true
        hitButton.isEnabled = true
        bustOrBlackjackText.text = ""
        whoWinsText.text = ""
        clearAllCards()
        playersHand.clear()
        dealersHand.clear()
        dealerWins = false
        playerWins = false
        draw = false
        playerTotal = 0
        cardDeck.addAll(usedCards)
        cash -= 10
        cashTextView.text = "Cash: $cash"
        dealCardToPlayer()
        dealCardToPlayer()
        // deal 1st card to dealer and display back of hole card
        val dealers1stCard = drawCard()
        dealersHand.add(dealers1stCard)
        dealers1stCardImage.setImageDrawable(getCardImage(dealers1stCard))
        dealers2ndCardImage.setImageResource(R.drawable.backside)
        //visa totalen för både dealern och spelaren
        dealerTotal = calculateCardValue(dealers1stCard, 0)
        dealerTotalTextView.text =
            "Dealer: $dealerTotal"
    }

    private fun clearAllCards() {
        players1stCardImage.setImageDrawable(null)
        players2ndCardImage.setImageDrawable(null)
        players3rdCardImage.setImageDrawable(null)
        players4thCardImage.setImageDrawable(null)
        players5thCardImage.setImageDrawable(null)
        players6thCardImage.setImageDrawable(null)
        players7thCardImage.setImageDrawable(null)
        players8thCardImage.setImageDrawable(null)
        players9thCardImage.setImageDrawable(null)
        dealers1stCardImage.setImageDrawable(null)
        dealers2ndCardImage.setImageDrawable(null)
        dealers3rdCardImage.setImageDrawable(null)
        dealers4thCardImage.setImageDrawable(null)
        dealers5thCardImage.setImageDrawable(null)
        dealers6thCardImage.setImageDrawable(null)
        dealers7thCardImage.setImageDrawable(null)
    }


    private fun calculateCardValue(card: Card, subTotal: Int): Int {
        if (card.cardValue == 1) {
            if (subTotal > 10) {
                return 1
            } else {
                return 11
            }
        } else if (card.cardValue > 10) {
            return 10
        } else {
            return card.cardValue
        }

    }

    private fun dealCardToPlayer() {
        val card = drawCard()
        playersHand.add(card)
        playerTotal = calculateTotal(true)
        playerTotalTextView.text = "Player: $playerTotal"

        when (playersHand.size) {
            1 -> players1stCardImage.setImageDrawable(getCardImage(card))
            2 -> {
                players2ndCardImage.setImageDrawable(getCardImage(card))
                if (playerTotal == 21) {
                    bustOrBlackjackText.text = "BLACKJACK"
                    blackJack = true
                    val card1 = playersHand[0]
                    val card2 = playersHand[1]
                    // the following checks if the player has a "special blackjack"
                    // which wins immediately
                    if (card1.cardValue == 1 && card1.suit == "spades") {
                        if (card2.cardValue == 11) {
                            if (card2.suit == "spades" || card2.suit == "clubs") {
                                playerWins = true
                                cash += specialBlackJackBonus
                            }
                        }
                    } else if (card2.cardValue == 1 && card2.suit == "spades") {
                        if (card1.cardValue == 11) {
                            if (card1.suit == "spades" || card1.suit == "clubs") {
                                playerWins = true
                                cash += specialBlackJackBonus
                            }
                        }
                    }
                    stand()
                }
            }

            3 -> players3rdCardImage.setImageDrawable(getCardImage(card))
            // when more than 3, move 1st card to 4th ImageView etc
            4 -> {
                players1stCardImage.setImageDrawable(null)
                players2ndCardImage.setImageDrawable(null)
                players3rdCardImage.setImageDrawable(null)
                players4thCardImage.setImageDrawable(getCardImage(playersHand[0]))
                players5thCardImage.setImageDrawable(getCardImage(playersHand[1]))
                players6thCardImage.setImageDrawable(getCardImage(playersHand[2]))
                players7thCardImage.setImageDrawable(getCardImage(playersHand[3]))
                players4thCardImage.elevation = 1.0F
                players5thCardImage.elevation = 2.0F
                players6thCardImage.elevation = 3.0F
                players7thCardImage.elevation = 4.0F
            }

            5 -> {
                players8thCardImage.setImageDrawable(getCardImage(playersHand[4]))
                players8thCardImage.elevation = 5.0F
            }

            6 -> {
                players9thCardImage.setImageDrawable(getCardImage(playersHand[5]))
                players9thCardImage.elevation = 6.0F
                stand() // ends round, as maximum six cards are allowed for player
            }
        }
        if (checkForBust(playerTotal)) {
            dealerWins = true
            playBustSound = MediaPlayer.create(this, R.raw.bust_drum)
            if (!playBustSound.isPlaying) {
                playBustSound.start()
            }
            bustOrBlackjackText.text = "BUST"
            finishRound()
        }
        if (playerTotal == 21) {
            stand()
        }
    }


    private fun checkForBust(total: Int): Boolean {
        if (total > 21) {
            return true
        }
        return false
    }

    private fun getCardImage(card: Card): Drawable? {
        when (card.suit) {
            "clubs" -> when (card.cardValue) {
                1 -> return ResourcesCompat.getDrawable(resources, R.drawable.ace_clubs, null)
                2 -> return ResourcesCompat.getDrawable(resources, R.drawable.two_clubs, null)
                3 -> return ResourcesCompat.getDrawable(resources, R.drawable.three_clubs, null)
                4 -> return ResourcesCompat.getDrawable(resources, R.drawable.four_clubs, null)
                5 -> return ResourcesCompat.getDrawable(resources, R.drawable.five_clubs, null)
                6 -> return ResourcesCompat.getDrawable(resources, R.drawable.six_clubs, null)
                7 -> return ResourcesCompat.getDrawable(resources, R.drawable.seven_clubs, null)
                8 -> return ResourcesCompat.getDrawable(resources, R.drawable.eight_clubs, null)
                9 -> return ResourcesCompat.getDrawable(resources, R.drawable.nine_clubs, null)
                10 -> return ResourcesCompat.getDrawable(resources, R.drawable.ten_clubs, null)
                11 -> return ResourcesCompat.getDrawable(resources, R.drawable.jack_clubs, null)
                12 -> return ResourcesCompat.getDrawable(resources, R.drawable.queen_clubs, null)
                13 -> return ResourcesCompat.getDrawable(resources, R.drawable.king_clubs, null)
            }

            "diamonds" -> when (card.cardValue) {
                1 -> return ResourcesCompat.getDrawable(resources, R.drawable.ace_diamonds, null)
                2 -> return ResourcesCompat.getDrawable(resources, R.drawable.two_diamonds, null)
                3 -> return ResourcesCompat.getDrawable(resources, R.drawable.three_diamonds, null)
                4 -> return ResourcesCompat.getDrawable(resources, R.drawable.four_diamonds, null)
                5 -> return ResourcesCompat.getDrawable(resources, R.drawable.five_diamonds, null)
                6 -> return ResourcesCompat.getDrawable(resources, R.drawable.six_diamonds, null)
                7 -> return ResourcesCompat.getDrawable(resources, R.drawable.seven_diamonds, null)
                8 -> return ResourcesCompat.getDrawable(resources, R.drawable.eight_diamonds, null)
                9 -> return ResourcesCompat.getDrawable(resources, R.drawable.nine_diamonds, null)
                10 -> return ResourcesCompat.getDrawable(resources, R.drawable.ten_diamonds, null)
                11 -> return ResourcesCompat.getDrawable(resources, R.drawable.jack_diamonds, null)
                12 -> return ResourcesCompat.getDrawable(resources, R.drawable.queen_diamonds, null)
                13 -> return ResourcesCompat.getDrawable(resources, R.drawable.king_diamonds, null)
            }

            "hearts" -> when (card.cardValue) {
                1 -> return ResourcesCompat.getDrawable(resources, R.drawable.ace_hearts, null)
                2 -> return ResourcesCompat.getDrawable(resources, R.drawable.two_hearts, null)
                3 -> return ResourcesCompat.getDrawable(resources, R.drawable.three_hearts, null)
                4 -> return ResourcesCompat.getDrawable(resources, R.drawable.four_hearts, null)
                5 -> return ResourcesCompat.getDrawable(resources, R.drawable.five_hearts, null)
                6 -> return ResourcesCompat.getDrawable(resources, R.drawable.six_hearts, null)
                7 -> return ResourcesCompat.getDrawable(resources, R.drawable.seven_hearts, null)
                8 -> return ResourcesCompat.getDrawable(resources, R.drawable.eight_hearts, null)
                9 -> return ResourcesCompat.getDrawable(resources, R.drawable.nine_hearts, null)
                10 -> return ResourcesCompat.getDrawable(resources, R.drawable.ten_hearts, null)
                11 -> return ResourcesCompat.getDrawable(resources, R.drawable.jack_hearts, null)
                12 -> return ResourcesCompat.getDrawable(resources, R.drawable.queen_hearts, null)
                13 -> return ResourcesCompat.getDrawable(resources, R.drawable.king_hearts, null)
            }

            "spades" -> when (card.cardValue) {
                1 -> return ResourcesCompat.getDrawable(resources, R.drawable.ace_spades, null)
                2 -> return ResourcesCompat.getDrawable(resources, R.drawable.two_spades, null)
                3 -> return ResourcesCompat.getDrawable(resources, R.drawable.three_spades, null)
                4 -> return ResourcesCompat.getDrawable(resources, R.drawable.four_spades, null)
                5 -> return ResourcesCompat.getDrawable(resources, R.drawable.five_spades, null)
                6 -> return ResourcesCompat.getDrawable(resources, R.drawable.six_spades, null)
                7 -> return ResourcesCompat.getDrawable(resources, R.drawable.seven_spades, null)
                8 -> return ResourcesCompat.getDrawable(resources, R.drawable.eight_spades, null)
                9 -> return ResourcesCompat.getDrawable(resources, R.drawable.nine_spades, null)
                10 -> return ResourcesCompat.getDrawable(resources, R.drawable.ten_spades, null)
                11 -> return ResourcesCompat.getDrawable(resources, R.drawable.jack_spades, null)
                12 -> return ResourcesCompat.getDrawable(resources, R.drawable.queen_spades, null)
                13 -> return ResourcesCompat.getDrawable(resources, R.drawable.king_spades, null)
            }

        }
        return null
    }

    private fun drawCard(): Card {
        val randomIndex: Int = Random.nextInt(cardDeck.size)
        val card = cardDeck[randomIndex]
        cardDeck.removeAt(randomIndex)
        usedCards.add(card)
        if (!cardSound.isPlaying) {
            cardSound.start()
        }
        return card
    }

    private fun stand() {
        hitButton.isEnabled = false
        standButton.isEnabled = false
        if (playerWins) { // ie if specialBlackjack, see dealCardToPlayer()
            finishRound()
        } else {
            dealerPlays()
        }
    }

    private fun dealerPlays() {
        dealers2ndCardImage.setImageDrawable(null) //removes the "hole card" image
        val card2 = drawCard()
        dealersHand.add(card2)
        dealers2ndCardImage.setImageDrawable(getCardImage(dealersHand[1]))
        dealerTotal = calculateTotal(false)
        dealerTotalTextView.text = "Dealer: $dealerTotal"
        if (dealerTotal == 21) { // check if player also has blackjack:
            if (playersHand.size == 2 && playerTotal == 21) {
                draw = true
            } else {
                dealerWins = true
            }
        }
        while (dealerTotal < 17 && dealerTotal <= playerTotal && dealersHand.size < 5) {
            val card = drawCard()
            dealersHand.add(card)
            dealerTotal = calculateTotal(false)
            dealerTotalTextView.text = "Dealer: $dealerTotal"

            when (dealersHand.size) {
                3 -> {
                    // when more than 2, move 1st card to 3rd ImageView etc
                    dealers1stCardImage.setImageDrawable(null)
                    dealers2ndCardImage.setImageDrawable(null)
                    dealers3rdCardImage.setImageDrawable(getCardImage(dealersHand[0]))
                    dealers4thCardImage.setImageDrawable(getCardImage(dealersHand[1]))
                    dealers5thCardImage.setImageDrawable(getCardImage(dealersHand[2]))
                    dealers3rdCardImage.elevation = 1.0F
                    dealers4thCardImage.elevation = 2.0F
                    dealers5thCardImage.elevation = 3.0F

                }

                4 -> {
                    dealers6thCardImage.setImageDrawable(getCardImage(dealersHand[3]))
                    dealers6thCardImage.elevation = 4.0F
                }

                5 -> {
                    dealers7thCardImage.setImageDrawable(getCardImage(dealersHand[4]))
                    dealers6thCardImage.elevation = 5.0F

                }
            }
            if (checkForBust(dealerTotal)) {
                playerWins = true
            }
        }
        finishRound()
    }

    private fun finishRound() {
        hitButton.isEnabled = false
        standButton.isEnabled = false
        if (!dealerWins && !playerWins && !draw) {
            if (playerTotal < 22 && playerTotal > dealerTotal) playerWins = true
            else if (dealerTotal < 22 && playerTotal < dealerTotal) dealerWins = true
            else if (playerTotal == dealerTotal) draw = true
        }
        if (dealerWins) {
            whoWinsText.setTextColor(Color.parseColor("#FF0000"))
            whoWinsText.text = "DEALER WINS"
            if (blackJack) {
                bustOrBlackjackText.text = "DEALER HAS BLACKJACK"
            }
        } else if (playerWins) {
            whoWinsText.setTextColor(Color.parseColor("#800080"))
            whoWinsText.text = "YOU WIN!"
            cash += 20
            if (!winnerSound.isPlaying) {
                winnerSound.start()
            }
            if (blackJack) {
                playerTotal += blackJackBonus
                bustOrBlackjackText.text = "BLACKJACK!"
            }
            cashTextView.text = "Cash: $cash"
        } else {
            whoWinsText.text = "DRAW"
            cash += 10
            cashTextView.text = "Cash: $cash"
        }
        playAgainButton.show()
        playAgainButton.isEnabled = true
        endGameButton.show()
        endGameButton.isEnabled = true


    }

    private fun View.show() {
        this.visibility = VISIBLE
    }

    private fun View.makeInvisible() {
        this.visibility = INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        cardSound.release()
        playBustSound.release()
        winnerSound.release()
    }
}