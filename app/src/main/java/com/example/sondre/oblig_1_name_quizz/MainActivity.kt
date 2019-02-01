package com.example.sondre.oblig_1_name_quizz


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


/*
    Koden er ikke perfekt, er flere steder samme kode brukes flere ganger.
    Trenger endel opprydning...

    TODO: Mangler legg til eksisterende bilde...
 */


class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null

    companion object {
        val PREF_NAME : String = "PrefFile"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Sjekkar om det er shared preferences frå før, visst ikkje kjem dialog-boks opp
        checkPreferences()

        db = AppDatabase.getInstance(this)
        val personList : List<Person>? = db?.personDao()?.getAll()

        Log.i("ours", personList?.size.toString())

        val databaseButton = findViewById<Button>(R.id.picture) as Button
        databaseButton.setOnClickListener{
            intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }

        val quizButton = findViewById<Button>(R.id.start)
        quizButton.setOnClickListener{
            if(personList?.size != 0) {
                intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
            } else {
                noPicturesAlert()
            }

    }}

    fun checkPreferences() {
        val prefs : SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val ownerText : String? = prefs.getString("owner", null)
        val ownerTxtView : TextView =  findViewById<TextView>(R.id.owner)

        if (ownerText != null) {
            val name: String = prefs.getString("owner", "No owner defined")
            ownerTxtView.setText("Logged in as: " + ownerText)
        } else {
            val editor: SharedPreferences.Editor = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
            var ownerName: String = ""

            //Alert hvor man skriver inn owner
            val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it) }
            builder?.setTitle("Owner")

            val input: EditText = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            builder?.setView(input)

            builder?.setPositiveButton("Add Owner") { dialog, which ->
                ownerName = input.text.toString()

                //Edit sharedpreferences
                editor.putString("owner", ownerName)
                editor.apply()


                ownerTxtView.setText("Logged in as: " + ownerName)
            }
            builder?.show()
        }
    }
    fun changeName() {
        val ownerTxtView : TextView =  findViewById<TextView>(R.id.owner)

            val editor: SharedPreferences.Editor = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
            var ownerName: String = ""


            //Alert hvor man skriver inn owner
            val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it) }
            builder?.setTitle("Owner")

            val input: EditText = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            builder?.setView(input)

            builder?.setPositiveButton("Change owner") { dialog, which ->
                ownerName = input.text.toString()

                //Edit sharedpreferences
                editor.putString("owner", ownerName)
                editor.apply()


                ownerTxtView.setText("Logged in as: " + ownerName)

            }
            builder?.show()


        }



    //Alert hvis det ikke er lagt til noen bider...
    fun noPicturesAlert(){
        val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it)}

        builder?.setMessage(R.string.noPictureMsg)?.setTitle(R.string.noPictureError)

        builder?.setPositiveButton(R.string.addPicture){dialog, which ->
            intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }

        builder?.setNegativeButton(R.string.cancel) {dialog, which -> }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        /* val prefs = findViewById<Button>(R.id.shared_prefs)
        prefs.setOnClickListener {
            Log.i("Ours", "Clicked")
        } */


        return true
    }

     fun openSharedPreferences() {
        val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it)}
        val alertMsg = "Logged in as "

        builder?.setMessage(alertMsg)?.setTitle("Shared Preferences")

        //Brukeren trykket på ok knappen
        builder?.setPositiveButton("Ok"){dialog, which ->
        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }



    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        R.id.shared_prefs -> consume {
            changeName()
        }
    else -> super.onOptionsItemSelected(item)
    }

    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }


}


