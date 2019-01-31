package com.example.sondre.oblig_1_name_quizz

import android.app.AlertDialog
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler

import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button


import kotlinx.android.synthetic.main.activity_main.*

/*
    Koden er ikke perfekt, er flere steder samme kode brukes flere ganger.
    Trenger endel opprydning...

    TODO: Mangler legg til eksisterende bilde...
 */


class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null


    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)







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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.shared_prefs -> true


            else -> super.onOptionsItemSelected(item)
        }
    }
}
