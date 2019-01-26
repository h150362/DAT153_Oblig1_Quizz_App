package com.example.sondre.oblig_1_name_quizz


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView
import android.widget.LinearLayout


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
        val imageView = ImageView(this)


        val layout = findViewById(R.id.quizLinear) as LinearLayout

        //Setter størrelse på bilde, og legger det til layouten.
        val lp = LinearLayout.LayoutParams(width, height)
        imageView.setLayoutParams(lp)
        layout.addView(imageView)

        //TODO: LEgge til felt til svar. - EditTExt
        //TODO: Legge til random trekking av bilde...
        //Deklarerer listen bestående av alle person objektene
        val personList : List<Person>? = db?.personDao()?.getAll()

        if(personList?.size != null) {
            //Henter bare index 0 foreløpig
            val path = personList.get(0).picturePath
            val name = personList.get(0).first_name

            //Dekoder bildet
            val bmImg: Bitmap = BitmapFactory.decodeFile(path)
            imageView.setImageBitmap(bmImg)
        }



    }

}
