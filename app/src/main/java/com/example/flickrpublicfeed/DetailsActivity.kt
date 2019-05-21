package com.example.flickrpublicfeed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.flickrpublicfeed.fragments.SimilarPicturesAdapter
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val record = intent.getParcelableExtra(FPF_KEY_RECORD) as Record
        val similarPictures = intent.getStringArrayListExtra(FPF_KEY_SIMILAR_PHOTOS)

        val viewPager = fragmentSwitcher
        viewPager.adapter = SimilarPicturesAdapter(record, similarPictures, supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId

        if (id == R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val FPF_KEY_RECORD = "FPF_KEY_RECORD"
        const val FPF_KEY_SIMILAR_PHOTOS = "FPF_KEY_SIMILAR_PHOTOS"
    }
}