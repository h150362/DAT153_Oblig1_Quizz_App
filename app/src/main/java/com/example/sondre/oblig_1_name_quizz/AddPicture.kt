package com.example.sondre.oblig_1_name_quizz

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Room
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore

import android.support.v4.content.FileProvider

import android.util.Log
import android.view.Gravity
import android.widget.*


import java.io.File
import java.io.IOException
import java.util.*


class AddPicture : Activity() {


    //Bruk til å vise bildet
    /*Bitmap bmImg = BitmapFactory.decodeFile("path of your img1");
imageView.setImageBitmap(bmImg);
*/

    private var db: AppDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)
        //setSupportActionBar(toolbar)

        db = AppDatabase.getInstance(this)

        val newPicture = findViewById<Button>(R.id.newPhoto)
        newPicture.setOnClickListener{
            dispatchTakePictureIntent()
        }




    }



    var mCurrentPhotoPath: String = ""
    val REQUEST_TAKE_PHOTO = 1


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath

        }
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }
    override fun onActivityResult(c:Int,r:Int,data:Intent){
        if (c == REQUEST_TAKE_PHOTO && r == RESULT_OK) {
            Log.i("Ours", "Test123")
            val person = Person(uid = 0, first_name = "Atle", picturePath = mCurrentPhotoPath)
            Log.i("Ours","Person made! " + person.picturePath)
            db?.personDao()?.insertPerson(person)

            //Størrelse på bilde i piksler
            val height: Int = (300 * resources.displayMetrics.density).toInt()
            val width: Int = (300 * resources.displayMetrics.density).toInt()
            val imageView = ImageView(this)


            val layout = findViewById(R.id.addPicLinear) as LinearLayout


            //Setter størrelse på bilde, og legger det til layouten.
            val lp = LinearLayout.LayoutParams(width, height)
            imageView.setLayoutParams(lp)
            layout.addView(imageView)

            //EditText hvor man skriver inn navnet på personen.
            val editText = EditText(this)
            editText.setHint(R.string.namePerson)
            editText.setGravity(Gravity.CENTER)
            layout.addView(editText)

            //Knapp
            

            //Dekoder bildet
            val bmImg:Bitmap = BitmapFactory.decodeFile(person.picturePath)
            imageView.setImageBitmap(bmImg);

        }






        //val test = db?.personDao()?.getAll()?.isEmpty()
       // db.personDao().insertAll(person)
        //Manager.getInstance().savePerson(person);


} }
