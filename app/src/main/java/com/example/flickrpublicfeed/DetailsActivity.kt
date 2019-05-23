package com.example.flickrpublicfeed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.flickrpublicfeed.fragments.SimilarPicturesAdapter
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val record = intent.getParcelableExtra(FPF_KEY_RECORD) as Record
        val similarPictures = intent.getStringArrayListExtra(FPF_KEY_SIMILAR_PHOTOS)

        viewPager = this.fragmentSwitcher
        viewPager.adapter = SimilarPicturesAdapter(record, similarPictures, supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId

        if (id == R.id.add) {
            if (viewPager.currentItem == 1) {
                viewPager.currentItem = 0
            } else {
                viewPager.currentItem = 1
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val FPF_KEY_RECORD = "FPF_KEY_RECORD"
        const val FPF_KEY_SIMILAR_PHOTOS = "FPF_KEY_SIMILAR_PHOTOS"
    }
}