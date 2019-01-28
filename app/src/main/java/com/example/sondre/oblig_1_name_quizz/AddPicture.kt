package com.example.sondre.oblig_1_name_quizz

import android.accessibilityservice.AccessibilityService
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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
import android.view.View
import android.widget.*


import java.io.File
import java.io.IOException
import java.util.*


class AddPicture : Activity() {


    private var db: AppDatabase? = null

    var mCurrentPhotoPath: String = ""
    val REQUEST_TAKE_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)

        db = AppDatabase.getInstance(this)

        //
        dispatchTakePictureIntent()

        val newPicture = findViewById<Button>(R.id.newPhoto)
        newPicture.setOnClickListener{
            dispatchTakePictureIntent()
        }


    }

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

    //Etter bildet er tatt...
    override fun onActivityResult(c:Int,r:Int,data:Intent){
        if (c == REQUEST_TAKE_PHOTO && r == RESULT_OK) {
            val person = Person()
            //val person = Person(uid = 0, first_name = "Atle", picturePath = mCurrentPhotoPath)


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

            //Deklarering av knapp
            val btn = Button(this)
            btn.setText(R.string.addPerson)
            btn.gravity=Gravity.CENTER
            layout.addView(btn)


            //Legger til personen i databasen
            btn.setOnClickListener{v: View? ->
                person.first_name=editText.text.toString()
                person.picturePath=mCurrentPhotoPath
                person.uid=0
                Log.i("Ours", "TEST" + person)
                db?.personDao()?.insertPerson(person)

                //TODO: Legge inn bekreftelse på at den er lagt til. Toast feks.
                //og tilbake til Datactivity?
                addedToDataBaseAlert()

            }



            //Dekoder bildet
            val bmImg:Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            imageView.setImageBitmap(bmImg)

        }

}
    //
    fun addedToDataBaseAlert() {

        val builder: AlertDialog.Builder? = this@AddPicture?.let { AlertDialog.Builder(it)}

        builder?.setMessage(R.string.personAdded)?.setTitle(R.string.personAdded)

        builder?.setPositiveButton("Ok"){dialog, which ->
            //Brukeren trykket på ok knappen
            intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }



        val dialog: AlertDialog? = builder?.create()
        dialog?.show()

    }
}
