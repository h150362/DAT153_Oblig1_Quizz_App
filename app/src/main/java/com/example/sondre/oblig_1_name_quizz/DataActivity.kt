package com.example.sondre.oblig_1_name_quizz

import android.app.AlertDialog
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.util.Log


import android.view.Gravity
import android.widget.*


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
            //intent = Intent(this, AddPicture::class.java)
            //startActivity(intent)
            addNewPictureAlert()
        }

        //Start spill knapp
        val quizButton = findViewById<Button>(R.id.startGame)
        quizButton.setOnClickListener{
            intent = Intent(this, QuizActivity::class.java)
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

    fun addNewPictureAlert() {
        val builder: AlertDialog.Builder? = this@DataActivity?.let { AlertDialog.Builder(it)}

        //  val alertText = TextView(this)
        // alertText.setText(R.string.personAdded)
        // alertText.gravity = Gravity.CENTER


        // builder?.setCustomTitle(alertText)
        //builder?.setView(alertText)
        builder?.setMessage(R.string.AddNewPictureAlertMsg)
            ?.setTitle(R.string.addNewPictureAlertTitle)

        builder?.setPositiveButton(R.string.newPhoto){dialog, which ->
            //Brukeren trykket på ok knappen
            intent = Intent(this, AddPicture::class.java)
            startActivity(intent)
        }

        //TODO: Funker ikke enda...

        builder?.setNegativeButton(R.string.existingPhoto) {dialog, which ->
           // intent = Intent(this, AddPicture::class.java)g/*
        }

        //Avbryt knapp.
       builder?.setNeutralButton(R.string.cancel) {dialog, which ->
           Toast.makeText(applicationContext, R.string.canceled, Toast.LENGTH_SHORT).show()
       }


        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }
}


