package com.example.sondre.oblig_1_name_quizz


import android.R.*
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.*



import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.content_quiz.*
import org.w3c.dom.Text

//Todo: mulighet for å slutte spillet midt i.

class QuizActivity : AppCompatActivity() {

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setSupportActionBar(toolbar)

        //henter databasen
        db = AppDatabase.getInstance(this)


        //Størrelse på bilde i piksler
        val height: Int = (300 * resources.displayMetrics.density).toInt()
        val width: Int = (300 * resources.displayMetrics.density).toInt()
        var imageView = ImageView(this)

        //Deklarerer listen bestående av alle person objektene
        val personList : List<Person>? = db?.personDao()?.getAll()


        val layout = findViewById(R.id.quizLinear) as LinearLayout

        //Setter størrelse på bilde, og legger det til layouten.
        val lp = LinearLayout.LayoutParams(width, height)
        imageView.setLayoutParams(lp)
        layout.addView(imageView)


        var name : String? = ""
        var path : String? = ""

        var theScore : Int = 0
        var i : Int = 0
        val editText = findViewById<EditText>(R.id.answer)
        val score = findViewById<TextView>(R.id.score)
        val quizButton = findViewById<Button>(R.id.submit) as Button

        //Visst det er entries i databasen
        if(personList?.size != 0) {

            path = personList!!.get(i).picturePath
            name = personList.get(i).first_name
            var bmImg: Bitmap = BitmapFactory.decodeFile(path)
            imageView.setImageBitmap(bmImg)


            Log.i("ours", personList.size.toString())

            //Sjekker om svar er riktig, setter nytt bilde
            quizButton.setOnClickListener {

                val inputSvar = editText.text.toString()
                if(checkAnswer(inputSvar, name!!)) {
                    //Riktig svar
                    theScore = theScore.inc()
                    correctAnswerToast()
                } else {
                    wrongAnswerAlert(inputSvar, name!!)
                }

                //Score textView...
                val textScore : String = resources.getString(R.string.scoreTxt)
                score.text = textScore + ": " + theScore.toString() + "/" + (i+1)



                if(i < personList.size -1) {
                    i = i.inc()
                } else {
                    //Spillet er ferdig.
                    gameFinishedAlert(theScore, i+1)
                }

                //Henter på nytt
                path = personList.get(i).picturePath
                name = personList.get(i).first_name
                bmImg = BitmapFactory.decodeFile(path)
                imageView.setImageBitmap(bmImg)


         }

    }

    }

    fun gameFinishedAlert(score : Int, antall : Int){

        val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it)}
        val gameFinishedTxt = resources.getString(R.string.yourScore) + score + "/" +  antall

        builder?.setMessage(gameFinishedTxt)?.setTitle(R.string.gameFinished)

        //Brukeren trykket på ok knappen
        builder?.setPositiveButton(R.string.newGame){dialog, which ->
            intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        builder?.setNegativeButton(R.string.mainMenu) {dialog, which ->
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

     fun checkAnswer(input: String, correct: String): Boolean{
        if(input.equals(correct)) {
            return true
        }
            return false
        }


    //Pop-up boks for feil svar.
    fun wrongAnswerAlert(answer: String, correct: String) {

        val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it)}
        val alertMsg = resources.getString(R.string.correctAnswerIs) + correct +"!\n" +
                resources.getString(R.string.yourAnswerWas)

        builder?.setMessage(alertMsg)?.setTitle(R.string.wrongAnswerAlert)

        //Brukeren trykket på ok knappen
        builder?.setPositiveButton("Ok"){dialog, which ->
        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }


    //Toast melding
    fun correctAnswerToast() {
        val toast = Toast.makeText(applicationContext, R.string.correctAnswer, Toast.LENGTH_LONG)
        toast.show()
    }

}
