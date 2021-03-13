package com.example.foto

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var Imagem: ImageView
    private lateinit var filePhoto: File
    private val FILE_NAME = "photo.jpg"
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.Imagem = findViewById(R.id.Imagem)
        this.Imagem.setOnClickListener({ tirarFoto(it) })
    }

    private fun tirarFoto(view: View){
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = exibirFoto(FILE_NAME)
        val providerFile = FileProvider.getUriForFile(this,"com.example.androidcamera.fileprovider", filePhoto)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        startActivityForResult(takePhotoIntent, REQUEST_CODE)
        exibirFoto(filePhoto.name);
    }

    private fun exibirFoto(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            Imagem.setImageBitmap(takenPhoto)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Imagem.setImageURI(data?.data)
        }
    }

}