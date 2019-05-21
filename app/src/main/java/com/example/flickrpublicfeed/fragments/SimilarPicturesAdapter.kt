package com.example.flickrpublicfeed.fragments


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.flickrpublicfeed.Record


class SimilarPicturesAdapter(
    private val record: Record,
    private val similarPhotos: ArrayList<String>,
    fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> Picture.newInstance(record)
            1 -> Split.newInstance(record, similarPhotos)
            else -> Split.newInstance(record, similarPhotos)
        }
    }

    override fun getCount(): Int {
        return 2
    }
}