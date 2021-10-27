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
    private lateinit var hangManImg: ImageView
    private lateinit var word: TextView
    private lateinit var rnd: Random
    private val abc = "abcdefghijklmnopqrstuvwxyz".toCharArray() //26 elem
    private val words = arrayOf("teszt", "alma", "asztal", "eger", "table", "enemy", "cringe", "based", "sigma", "beta", "intel") //11 elem

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
    }

    private fun init() {
        lettersView = findViewById(R.id.lettersView)
        prevBtn = findViewById(R.id.prevBtn)
        nextBtn = findViewById(R.id.nextBtn)
        hangManImg = findViewById(R.id.hangManImg)
        word = findViewById(R.id.word)
        rnd = Random

        lettersView.append(abc[0].toString())

        val choosenWordArr = words[rnd.nextInt(11)].toCharArray()
        for(c in choosenWordArr) {
            word.append("_ ")
        }
    }

    private fun findIndex() : Int {
        var index = 0
        for(i in 0..abc.size-1) {
            if (abc[i].toString() == lettersView.text) index = i
        }
        return index
    }
}