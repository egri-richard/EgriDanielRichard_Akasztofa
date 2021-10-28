package com.example.akasztofa

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var lettersView: TextView
    private lateinit var prevBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var guessBtn : Button
    private lateinit var hangManImg: ImageView
    private lateinit var word: TextView
    private lateinit var gameCompleteAlert : AlertDialog.Builder
    private lateinit var rnd: Random
    private val abc = "abcdefghijklmnopqrstuvwxyz".toCharArray() //26 elem
    private val words = arrayOf("test", "apple", "table", "mouse", "rule", "enemy", "cry", "base", "sour", "band", "intel") //11 elem
    private lateinit var choosenWordArr : CharArray
    private var correctLetters : MutableList<Char> = mutableListOf()
    private var mistakes = 0
    private var wasGuessed : MutableList<Char> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        nextBtn.setOnClickListener {
            val i = if((abc.indexOf(lettersView.text.single()) + 1) > 25){
                0
            } else {
                abc.indexOf(lettersView.text.single()) + 1
            }

            lettersView.text = ""
            lettersView.append(abc[i].toString())
        }

        prevBtn.setOnClickListener {
            val i = if((abc.indexOf(lettersView.text.single()) - 1) < 0) {
                25
            } else {
                abc.indexOf(lettersView.text.single()) - 1
            }

            lettersView.text = ""
            lettersView.append(abc[i].toString())
        }

        guessBtn.setOnClickListener {
            val c = lettersView.text.single()

            if (wasGuessed.contains(c)) {
                Toast.makeText(this, "Ezt már próbáltad", Toast.LENGTH_SHORT).show()
            } else {
                wasGuessed.add(c)

                if (!guessTaken(c)) {
                    mistakes++
                    Toast.makeText(this, "Rossz tipp", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Jó tipp", Toast.LENGTH_SHORT).show()
                }
            }

            when(mistakes) {
                0 -> hangManImg.setImageResource(R.drawable.akasztofa0)
                1 -> hangManImg.setImageResource(R.drawable.akasztofa1)
                2 -> hangManImg.setImageResource(R.drawable.akasztofa2)
                3 -> hangManImg.setImageResource(R.drawable.akasztofa3)
                4 -> hangManImg.setImageResource(R.drawable.akasztofa4)
                5 -> hangManImg.setImageResource(R.drawable.akasztofa5)
                6 -> hangManImg.setImageResource(R.drawable.akasztofa6)
                7 -> hangManImg.setImageResource(R.drawable.akasztofa7)
                8 -> hangManImg.setImageResource(R.drawable.akasztofa8)
                9 -> hangManImg.setImageResource(R.drawable.akasztofa9)
                10 -> hangManImg.setImageResource(R.drawable.akasztofa10)
                11 -> hangManImg.setImageResource(R.drawable.akasztofa11)
                12 -> hangManImg.setImageResource(R.drawable.akasztofa12)
                13 -> hangManImg.setImageResource(R.drawable.akasztofa13)
            }

            if (!word.text.contains("_") || mistakes == 13) {
                gameComplete()
            }
        }
    }

    private fun reset() {
        mistakes = 0
        hangManImg.setImageResource(R.drawable.akasztofa0)
        lettersView.text = ""
        lettersView.append(abc[0].toString())

        correctLetters = mutableListOf()
        wasGuessed = mutableListOf()
        word.text = ""
        choosenWordArr = words[rnd.nextInt(11)].toCharArray()
        for(c in choosenWordArr) {
            word.append("_ ")
        }
    }
    
    private fun gameComplete() {
        if (mistakes == 13) {
            gameCompleteAlert.setTitle("Ön vesztett")
            gameCompleteAlert.show()
        } else {
            gameCompleteAlert.setTitle("Ön nyert")
            gameCompleteAlert.show()
        }
    }

    private fun guessTaken(guessedChar: Char) : Boolean {
        var isCorrect = false
        word.text = ""
        for(c in choosenWordArr) {
            if (correctLetters.contains(c)) {
                word.append("$c ")
            }
            else if (c == guessedChar) {
                word.append("$guessedChar ")
                correctLetters.add(guessedChar)
                isCorrect = true
            } else {
                word.append("_ ")
            }
        }

        return isCorrect
    }

    private fun init() {
        lettersView = findViewById(R.id.lettersView)
        prevBtn = findViewById(R.id.prevBtn)
        nextBtn = findViewById(R.id.nextBtn)
        guessBtn = findViewById(R.id.guessBtn)
        hangManImg = findViewById(R.id.hangManImg)
        word = findViewById(R.id.word)
        gameCompleteAlert = AlertDialog.Builder(this)
            .setCancelable(false)
            .setMessage("Szeretne új játékot kezdeni?")
            .setPositiveButton("Igen") { _, _-> reset()}
            .setNegativeButton("Nem") {_, _-> finish()}
        rnd = Random

        lettersView.append(abc[0].toString())

        choosenWordArr = words[rnd.nextInt(11)].toCharArray()

        for(c in choosenWordArr) {
            word.append("_ ")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putChar("currentChar", lettersView.text.single())
        outState.putCharArray("choosenWordArr", choosenWordArr)
        outState.putCharArray("correctLetters", correctLetters.toCharArray())
        outState.putInt("mistakes", mistakes)
        outState.putCharArray("wasGuessed", wasGuessed.toCharArray())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        choosenWordArr = savedInstanceState.getCharArray("choosenWordArr")!!
        correctLetters = savedInstanceState.getCharArray("correctLetters")!!.toMutableList()
        mistakes = savedInstanceState.getInt("mistakes")
        wasGuessed = savedInstanceState.getCharArray("wasGuessed")!!.toMutableList()
        lettersView.text = savedInstanceState.getChar("currentChar").toString()

        word.text = ""
        for(c in choosenWordArr) {
            if (correctLetters.contains(c)) {
                word.append("$c ")
            } else {
                word.append("_ ")
            }
        }

        when(mistakes) {
            0 -> hangManImg.setImageResource(R.drawable.akasztofa0)
            1 -> hangManImg.setImageResource(R.drawable.akasztofa1)
            2 -> hangManImg.setImageResource(R.drawable.akasztofa2)
            3 -> hangManImg.setImageResource(R.drawable.akasztofa3)
            4 -> hangManImg.setImageResource(R.drawable.akasztofa4)
            5 -> hangManImg.setImageResource(R.drawable.akasztofa5)
            6 -> hangManImg.setImageResource(R.drawable.akasztofa6)
            7 -> hangManImg.setImageResource(R.drawable.akasztofa7)
            8 -> hangManImg.setImageResource(R.drawable.akasztofa8)
            9 -> hangManImg.setImageResource(R.drawable.akasztofa9)
            10 -> hangManImg.setImageResource(R.drawable.akasztofa10)
            11 -> hangManImg.setImageResource(R.drawable.akasztofa11)
            12 -> hangManImg.setImageResource(R.drawable.akasztofa12)
            13 -> hangManImg.setImageResource(R.drawable.akasztofa13)
        }
    }
}