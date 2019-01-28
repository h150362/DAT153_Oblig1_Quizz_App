package com.example.sondre.oblig_1_name_quizz


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.*
import java.util.Random


import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private var db: AppDatabase? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setSupportActionBar(toolbar)


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
        if(personList?.size != null) {


            path = personList.get(i).picturePath
            name = personList.get(i).first_name
            var bmImg: Bitmap = BitmapFactory.decodeFile(path)
            imageView.setImageBitmap(bmImg)


            Log.i("ours", personList.size.toString())

            //Sjekker om svar er riktig, setter nytt bilde
            quizButton.setOnClickListener {

                val input = editText.text.toString()
                if(checkAnswer(input, name!!)) {
                theScore = theScore.inc()
                                   } else {
                    wrongAnswerAlert(input, name!!)
                }
                score.text = theScore.toString()

                val random = Random(42)
                var rnd = random.nextInt(personList.size)+0



                if(i < personList.size -1) {
                    i = i.inc()
                }

                //Henter på nytt
                path = personList.get(i).picturePath
                name = personList.get(i).first_name
                bmImg = BitmapFactory.decodeFile(path)
                imageView.setImageBitmap(bmImg)


                }

         }







    }

     fun checkAnswer(input: String, correct: String): Boolean{

        if(input.equals(correct)) {
            return true
        }
            return false
        }

    fun nextQuestion(length:Int, imageView: ImageView, path: String){


        //Dekoder bildet
        val bmImg: Bitmap = BitmapFactory.decodeFile(path)
        imageView.setImageBitmap(bmImg)


    }

    fun wrongAnswerAlert(answer: String, correct: String) {

        val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it)}

        builder?.setMessage("Riktig svar er " + correct + "Du svarte " +  answer)?.setTitle("Feil Svar!")

        builder?.setPositiveButton("Ok"){dialog, which ->
            //Brukeren trykket på ok knappen
           // intent = Intent(this, QuizActivity::class.java)
            //startActivity(intent)
        }



        val dialog: AlertDialog? = builder?.create()
        dialog?.show()

    }

}
