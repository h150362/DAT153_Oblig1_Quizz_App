package com.example.sondre.oblig_1_name_quizz

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.*

import kotlinx.android.synthetic.main.activity_data.*

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        setSupportActionBar(toolbar)



        val layout = findViewById<LinearLayout>(R.id.linear);
        val imgs:TypedArray = getResources().obtainTypedArray(R.array.QuizArray)

        for(i in 0..imgs.length()) {
           val image = imgs.getResourceId(i, 0)
            val imageText = imgs.getText(i)

            val imageView = ImageView(this)
            val textView = TextView(this)


            imageView.setImageResource(image)
            textView.setText(imageText)

            layout.addView(imageView)
            layout.addView(textView)
        }

        val AddButton = findViewById<Button>(R.id.AddPicture)
        AddButton.setOnClickListener{
            intent = Intent(this, AddPicture::class.java)
            startActivity(intent)


    }


}
    }
