package com.example.sondre.oblig_1_name_quizz

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button


import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null


    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        db = AppDatabase.getInstance(this)





        val databaseButton = findViewById<Button>(R.id.picture) as Button
        databaseButton.setOnClickListener{
            intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }

        val quizButton = findViewById<Button>(R.id.start)
        quizButton.setOnClickListener{
            intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
    }}

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
            else -> super.onOptionsItemSelected(item)
        }
    }
}
