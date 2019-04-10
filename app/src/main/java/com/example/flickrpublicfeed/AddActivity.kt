package com.example.flickrpublicfeed


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        imageDateButton!!.setOnClickListener {
            setDateButton()
        }
    }

    private fun setDateButton() {
        // create an OnDateSetListener

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            imageDateCheck.text = returnDate()
        }

        DatePickerDialog(
            this@AddActivity,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun addImageButton() {
        if (!checkIfEmpty()) {
            val dateText = imageDateCheck.text.toString()
            val tagString = imageTagsEdit.text.toString()
            val tags = ArrayList<String>()
            var tag = ""
            for (i: Char in tagString) {
                if (i == ';') {
                    tags.add(tag)
                    tag = ""
                } else {
                    tag += i
                }
            }
            if (tag.isNotEmpty()) {
                tags.add(tag)
            }



            val result = Record(
                imageURLEdit.text.toString(),
                imageNameEdit.text.toString(), tags, dateText
            )

            Toast.makeText(this, "Added!", Toast.LENGTH_LONG).show()
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

        if (imageTagsEdit.text.isBlank()) {
            imageTagsEditError()
            error = true
        }

        if (imageDateCheck.text.isBlank()) {
            imageDateCheckError()
            error = true
        }

        return error
    }

    private fun imageTagsEditError() {
        imageTagsEdit.error = "Give correct tags!"
    }

    private fun imageNameEditError() {
        imageNameEdit.error = "Give correct name!"
    }

    private fun imageURLEditError() {
        imageURLEdit.error = "Give correct URL!"
    }

    private fun imageDateCheckError() {
        imageDateCheck.error = "Choose date!"
    }

    private fun returnDate(): String =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(cal.time)
}
