package com.example.flickrpublicfeed


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddActivity : AppCompatActivity() {
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        addImage.setOnClickListener {
            addImageButton()
        }


        imageDateButton!!.setOnClickListener {
            setDateButton()
        }
    }

    private fun setDateButton() {

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            imageDateCheck.text = returnDate()
        }

        DatePickerDialog(
            this@AddActivity,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun addImageButton() {
        if (!checkIfEmpty()) {
            val dateText = imageDateCheck.text.toString()
            val imageURL = imageURLEdit.text.toString()
            val imageName = imageNameEdit.text.toString()

            val result = Record(imageURL, imageName, dateText, ArrayList())

            Toast.makeText(this, getString(R.string.FPF_ADD_TOAST_TEXT), Toast.LENGTH_LONG).show()
            val returnIntent = Intent()
            returnIntent.putExtra(MainActivity.KEY_FPF_RECEIVE, result)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }


    }



    private fun checkIfEmpty(): Boolean {

        var error = false
        if (imageURLEdit.text.isBlank()) {
            imageURLEditError()
            error = true
        }

        if (imageNameEdit.text.isBlank()) {
            imageNameEditError()
            error = true
        }

        if (imageDateCheck.text.isBlank()) {
            imageDateCheckError()
            error = true
        }

        return error
    }

    private fun imageNameEditError() {
        imageNameEdit.error = getString(R.string.FPF_CORRECT_MASS)
    }

    private fun imageURLEditError() {
        imageURLEdit.error = getString(R.string.FPF_CORRECT_URL)
    }

    private fun imageDateCheckError() {
        imageDateCheck.error = getString(R.string.FPF_CHOOSE_DATE)
    }

    private fun returnDate(): String =
        SimpleDateFormat(getString(R.string.FPF_DATE_FORMAT), Locale.getDefault()).format(cal.time)
}
