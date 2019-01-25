package com.example.sondre.oblig_1_name_quizz

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore

import android.support.v4.content.FileProvider

import android.util.Log
import android.widget.Button


import java.io.File
import java.io.IOException
import java.util.*

class AddPicture : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)
        //setSupportActionBar(toolbar)


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
        Log.i("Ours","Now at the right time!")
        val person = Person(uid = 1, first_name = "Atle", picturePath = mCurrentPhotoPath)

        db.personDao().insertAll(person)
        Manager.getInstance().savePerson(person);
        Log.i("Ours","Person made! ")

} }
