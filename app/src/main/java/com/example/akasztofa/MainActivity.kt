package com.example.akasztofa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var lettersView: TextView
    private lateinit var prevBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var guessBtn : Button
    private lateinit var hangManImg: ImageView
    private lateinit var word: TextView
    private lateinit var rnd: Random
    private val abc = "abcdefghijklmnopqrstuvwxyz".toCharArray() //26 elem
    private val words = arrayOf("teszt", "alma", "asztal", "eger", "table", "enemy", "cringe", "based", "sigma", "beta", "intel") //11 elem
    private lateinit var choosenWordArr : CharArray
    private val correctLetters : MutableList<Char> = mutableListOf()

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
            guessTaken(lettersView.text.single())
        }
    }

    private fun guessTaken(guessedChar: Char) {
        word.text = ""
        for(c in choosenWordArr) {
            if (correctLetters.contains(c)) {
                word.append("$c ")
            }
            else if (c == guessedChar) {
                word.append("$guessedChar ")
                correctLetters.add(guessedChar)
            } else {
                word.append("_ ")
            }
        }
    }

    private fun init() {
        lettersView = findViewById(R.id.lettersView)
        prevBtn = findViewById(R.id.prevBtn)
        nextBtn = findViewById(R.id.nextBtn)
        guessBtn = findViewById(R.id.guessBtn)
        hangManImg = findViewById(R.id.hangManImg)
        word = findViewById(R.id.word)
        rnd = Random

        lettersView.append(abc[0].toString())

        choosenWordArr = words[rnd.nextInt(11)].toCharArray()
        for(c in choosenWordArr) {
            word.append("_ ")
        }
    }
}