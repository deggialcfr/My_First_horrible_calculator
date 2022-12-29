package org.android.deggial.myfirsthorriblecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var cats = mutableListOf<Cats>()
    lateinit var buttonRunOver: Button
    lateinit var textViewName: TextView
    lateinit var textViewLegs: TextView
    lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRunOver = findViewById(R.id.purple_rectangle)
        textViewName = findViewById(R.id.textView2)
        textViewLegs = findViewById(R.id.textView)
        buttonNext = findViewById(R.id.button2)

        cats.add(Cats("Garfield","male",4,2,"green","orange",true,2,true))
        cats.add(Cats("nermal","female",3,2,"green","orange",true,2,true))
        cats.add(Cats("snowball 2","male",5,2,"green","orange",true,2,true))
        cats.add(Cats("Tom","male",4,2,"green","orange",true,2,true))
        cats.add(Cats("The Flying Cat","male",0,2,"green","orange",true,2,true))

        buttonRunOver.setOnClickListener { runOver ->
            cats.first().runOver()

            updateLabels()

            if(cats.first().legCount() == 0) {
                runOver.isEnabled = false
                buttonNext.visibility = View.VISIBLE
            }
        }

        buttonNext.setOnClickListener { next ->
            if (cats.size > 0) {
                cats.removeAt(0)

                buttonNext.visibility = View.GONE

                updateLabels()

                if (cats.first().legCount() > 0) {
                    buttonRunOver.isEnabled = true
                }
            } else
                next.isEnabled = false
        }

    }


    fun updateLabels(){
        textViewName.text = cats.first().catName()
        textViewLegs.text = cats.first().legCount().toString()
    }


    class Cats(private var name: String,
               private var genre: String,
               private var legs: Int = 4,
               private var eyes: Int = 2,
               private var eyeColor: String,
               private var hairColor: String,
               private var tail: Boolean = true,
               private var ears: Int = 2,
               private var nose: Boolean = true) {

//        fun newCat(name: String, genre: String,legs: Int, eyes:Int, eyeColor:String, hairColor: String, tail: Boolean, ears: Int, nose: Boolean){
//            this.name = name
//            this.genre = genre
//            this.legs = legs
//            this.eyes = eyes
//            this.eyeColor = eyeColor
//            this.hairColor = hairColor
//            this.tail = tail
//            this.ears = ears
//            this.nose = nose
//        }


        fun runOver() {
            if (legs > 0) {
                legs -= 1
            }
        }

        fun legCount(): Int{
            return legs
        }
        fun catName(): String{
            return name
        }
    }


}