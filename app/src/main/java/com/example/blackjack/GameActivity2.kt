package com.example.blackjack

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.view.View
import android.view.View.*

class GameActivity2 : AppCompatActivity() {

    private lateinit var standButton : Button
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
    private lateinit var mediaPlayer: MediaPlayer

    private var cash = 210
    //private lateinit var playerName: String
    private var blackJack = false
    var specialBlackJack = false
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

        //playerName = intent.getStringExtra("playerName")!!

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


        mediaPlayer = MediaPlayer.create(this, R.raw.cardsound)


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
        }
        else {
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
        cardDeck.addAll(usedCards) //TODO: Kontrollera att det här fungerar
        cash -= 10
        cashTextView.text = "Cash: $cash"
        dealCardToPlayer()
        dealCardToPlayer()
        // deal 1st card to dealer and display back of hole card
        val dealers1stCard = drawCard()
        dealersHand.add(dealers1stCard)
        dealers1stCardImage.setImageResource(getCardImage(dealers1stCard))
        dealers2ndCardImage.setImageResource(R.drawable.backside)
        //visa totalen för både dealern och spelaren
        dealerTotal = calculateCardValue(dealers1stCard, 0)
        dealerTotalTextView.text = "Dealer: $dealerTotal" //TODO: Om jag får tid, använd res. string m placeholder
        //playerTotalTextView.text = "Player: $playerTotal" //TODO: Samma här
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
        }
        else if (card.cardValue > 10) {
            return 10
        }
        else {
            return card.cardValue
        }

    }

    private fun dealCardToPlayer() {
        val card = drawCard()
        playersHand.add(card)
        playerTotal = calculateTotal(true)
        playerTotalTextView.text = "Player: $playerTotal"

        when (playersHand.size) {
            1 -> players1stCardImage.setImageResource(getCardImage(card))
            2 -> { players2ndCardImage.setImageResource(getCardImage(card))
            checkForBlackJack(playerTotal, playersHand.size, playersHand[0], playersHand[1])
            if (specialBlackJack) {
                playerWins = true
                cash += specialBlackJackBonus
                cashTextView.text = "Cash: $cash"
                // TODO play sound (fanfare etc)
                finishRound()
                return
            }
            else if (blackJack) {
                stand()
                return
            }
            }
            3 -> players3rdCardImage.setImageResource(getCardImage(card))
            // when more than 3, move 1st card to 4th ImageView etc
            4 -> {
                players1stCardImage.setImageDrawable(null)
                players2ndCardImage.setImageDrawable(null)
                players3rdCardImage.setImageDrawable(null)
                players4thCardImage.setImageResource(getCardImage(playersHand[0]))
                players5thCardImage.setImageResource(getCardImage(playersHand[1]))
                players6thCardImage.setImageResource(getCardImage(playersHand[2]))
                players7thCardImage.setImageResource(getCardImage(playersHand[3]))
            }
            5 -> players8thCardImage.setImageResource(getCardImage(playersHand[4]))
            6 -> {
                players9thCardImage.setImageResource(getCardImage(playersHand[5]))
                stand() // ends round, as maximum six cards are allowed for player
            }
        }
        if (checkForBust(playerTotal)) {
            dealerWins = true
            //TODO: spela något ljud
            bustOrBlackjackText.text = "BUST"
            finishRound()
        }
        if (playerTotal == 21) {
            finishRound()
        }
    }


    private fun checkForBust(total: Int): Boolean {
        if (total > 21) {
            return true
        }
        return false
    }

    private fun checkForBlackJack(total: Int, handSize: Int, card1: Card, card2: Card) {
        if (total == 21) {
            if (handSize == 2) {
                blackJack = true
                bustOrBlackjackText.text = "BLACKJACK"
                if (card1.cardValue == 1 && card1.suit == "spades") {
                    if (card2.cardValue == 11) {
                        if (card2.suit == "spades" || card2.suit == "clubs") {
                            specialBlackJack = true
                        }
                    }
                }
                else if (card2.cardValue == 1 && card2.suit == "spades") {
                    if (card1.cardValue == 11) {
                        if (card1.suit == "spades" || card1.suit == "clubs") {
                            specialBlackJack = true
                        }
                    }
                }
            }

    }
}

    private fun getCardImage(card: Card): Int {
        when (card.suit) {
            "clubs" -> when (card.cardValue) {
                1 -> return getDrawableResourceId(this, "ace_clubs")
                2 -> return getDrawableResourceId(this, "two_clubs")
                3 -> return getDrawableResourceId(this, "three_clubs")
                4 -> return getDrawableResourceId(this, "four_clubs")
                5 -> return getDrawableResourceId(this, "five_clubs")
                6 -> return getDrawableResourceId(this, "six_clubs")
                7 -> return getDrawableResourceId(this, "seven_clubs")
                8 -> return getDrawableResourceId(this, "eight_clubs")
                9 -> return getDrawableResourceId(this, "nine_clubs")
                10 -> return getDrawableResourceId(this, "ten_clubs")
                11 -> return getDrawableResourceId(this, "jack_clubs")
                12 -> return getDrawableResourceId(this, "queen_clubs")
                13 -> return getDrawableResourceId(this, "king_clubs")
            }
            "diamonds" -> when (card.cardValue) {
                1 -> return getDrawableResourceId(this, "ace_diamonds")
                2 -> return getDrawableResourceId(this, "two_diamonds")
                3 -> return getDrawableResourceId(this, "three_diamonds")
                4 -> return getDrawableResourceId(this, "four_diamonds")
                5 -> return getDrawableResourceId(this, "five_diamonds")
                6 -> return getDrawableResourceId(this, "six_diamonds")
                7 -> return getDrawableResourceId(this, "seven_diamonds")
                8 -> return getDrawableResourceId(this, "eight_diamonds")
                9 -> return getDrawableResourceId(this, "nine_diamonds")
                10 -> return getDrawableResourceId(this, "ten_diamonds")
                11 -> return getDrawableResourceId(this, "jack_diamonds")
                12 -> return getDrawableResourceId(this, "queen_diamonds")
                13 -> return getDrawableResourceId(this, "king_diamonds")
            }
            "hearts" -> when (card.cardValue) {
                1 -> return getDrawableResourceId(this, "ace_hearts")
                2 -> return getDrawableResourceId(this, "two_hearts")
                3 -> return getDrawableResourceId(this, "three_hearts")
                4 -> return getDrawableResourceId(this, "four_hearts")
                5 -> return getDrawableResourceId(this, "five_hearts")
                6 -> return getDrawableResourceId(this, "six_hearts")
                7 -> return getDrawableResourceId(this, "seven_hearts")
                8 -> return getDrawableResourceId(this, "eight_hearts")
                9 -> return getDrawableResourceId(this, "nine_hearts")
                10 -> return getDrawableResourceId(this, "ten_hearts")
                11 -> return getDrawableResourceId(this, "jack_hearts")
                12 -> return getDrawableResourceId(this, "queen_hearts")
                13 -> return getDrawableResourceId(this, "king_hearts")
            }
            "spades" -> when (card.cardValue) {
                1 -> return getDrawableResourceId(this, "ace_spades")
                2 -> return getDrawableResourceId(this, "two_spades")
                3 -> return getDrawableResourceId(this, "three_spades")
                4 -> return getDrawableResourceId(this, "four_spades")
                5 -> return getDrawableResourceId(this, "five_spades")
                6 -> return getDrawableResourceId(this, "six_spades")
                7 -> return getDrawableResourceId(this, "seven_spades")
                8 -> return getDrawableResourceId(this, "eight_spades")
                9 -> return getDrawableResourceId(this, "nine_spades")
                10 -> return getDrawableResourceId(this, "ten_spades")
                11 -> return getDrawableResourceId(this, "jack_spades")
                12 -> return getDrawableResourceId(this, "queen_spades")
                13 -> return getDrawableResourceId(this, "king_spades")
            }

        }
        return 0
    }
    private fun getDrawableResourceId(context: Context, drawableName: String): Int {
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }



    private fun drawCard() : Card {
        val randomIndex: Int = Random.nextInt(cardDeck.size)
        val card = cardDeck[randomIndex]
        cardDeck.removeAt(randomIndex)
        usedCards.add(card) //TODO: kontrollera att det här fungerar
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
        return card
    }

    private fun stand() {
        hitButton.isEnabled = false
        standButton.isEnabled = false
        dealerPlays()
    }

    private fun dealerPlays() {
        //TODO: ev delay här
        dealers2ndCardImage.setImageDrawable(null) //removes the "hole card" image
        val card2 = drawCard()
        dealersHand.add(card2)
        dealers2ndCardImage.setImageResource(getCardImage(dealersHand[1]))
        dealerTotal = calculateTotal(false)
        dealerTotalTextView.text = "Dealer: $dealerTotal"
        //TODO: ovanstående borde stämma
        checkForBlackJack(dealerTotal, dealersHand.size, dealersHand[0], dealersHand[1])
        if (blackJack) { // check if player also has blackjack:
            checkForBlackJack(playerTotal, playersHand.size, playersHand[0], playersHand[1])
            if (blackJack) {
                draw = true
            }
        }
        while (dealerTotal < 17 && dealersHand.size < 5) {
            //TODO: ev delay här
            val card = drawCard()
            dealersHand.add(card)
            dealerTotal = calculateTotal(false)
            dealerTotalTextView.text = "Dealer: $dealerTotal"


            when (dealersHand.size) {
                3 -> {
                    // when more than 2, move 1st card to 3rd ImageView etc
                    dealers1stCardImage.setImageDrawable(null)
                    dealers2ndCardImage.setImageDrawable(null)
                    dealers3rdCardImage.setImageResource(getCardImage(dealersHand[0]))
                    dealers4thCardImage.setImageResource(getCardImage(dealersHand[1]))
                    dealers5thCardImage.setImageResource(getCardImage(dealersHand[2]))
                }

                4 -> dealers6thCardImage.setImageResource(getCardImage(dealersHand[3]))
                5 -> {
                    dealers7thCardImage.setImageResource(getCardImage(dealersHand[4]))
                    finishRound() // ends round, as maximum five cards are allowed for dealer
                    return
                }
            }
            if (checkForBust(dealerTotal)) {
                playerWins = true
            }
        }
        finishRound()
    }

    private fun finishRound() {
        //TODO: text med DEALER WINS eller YOU WIN.
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
        }
        else if (playerWins) {
            whoWinsText.setTextColor(Color.parseColor("#800080"))
            whoWinsText.text = "YOU WIN!"
            cash += 20
            if (blackJack) playerTotal += blackJackBonus
            cashTextView.text = "Cash: $cash"
        }
        else {
            whoWinsText.text = "DRAW"
            cash += 10
            cashTextView.text = "Cash: $cash"
        }
        playAgainButton.show()
        playAgainButton.isEnabled = true
        endGameButton.show()
        endGameButton.isEnabled = true


    }




    fun View.hide() {
        this.visibility = GONE
    }

    private fun View.show() {
        this.visibility = VISIBLE
    }

    private fun View.makeInvisible(){
        this.visibility = INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Frigör resurserna använda av MediaPlayer
    }
}