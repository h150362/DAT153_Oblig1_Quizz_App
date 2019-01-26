package com.example.sondre.oblig_1_name_quizz

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.util.Log


import android.view.Gravity

import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


import kotlinx.android.synthetic.main.activity_data.*


class DataActivity : AppCompatActivity() {

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        setSupportActionBar(toolbar)

        //Konverterer dp til piksler
        val height: Int = (300 * resources.displayMetrics.density).toInt()
        val width: Int = (300 * resources.displayMetrics.density).toInt()
        val layout = findViewById<LinearLayout>(R.id.linear);

        //Database
        db = AppDatabase.getInstance(this)

        //Back knapp
        supportActionBar!!.title = resources.getString(R.string.back)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        //Legg til bilde knapp
        val AddButton = findViewById<Button>(R.id.AddPicture)
        AddButton.setOnClickListener {
            intent = Intent(this, AddPicture::class.java)
            startActivity(intent)
        }

        //Deklarerer listen bestående av alle person objektene
        val personList : List<Person>? = db?.personDao()?.getAll()

        //Sjekker om listen er tom.
        if(personList?.size != null) {
            for (i in 0.. personList.size-1) {

                //Imageview og textview
                val imgView = ImageView(this);
                val nameTextView = TextView(this)

                //Setter størrelse på bilde, og legger det til layouten.
                val lp = LinearLayout.LayoutParams(width, height)
                imgView.setLayoutParams(lp)
                layout.addView(imgView)

                //TextView: Navn på person
                nameTextView.setText(personList.get(i).first_name)
                nameTextView.gravity = Gravity.CENTER
                layout.addView(nameTextView)

                //Dekoder bildet
                val bmImg: Bitmap = BitmapFactory.decodeFile(personList.get(i).picturePath)
                imgView.setImageBitmap(bmImg)

            }
        }


    }


}

