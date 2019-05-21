package com.example.flickrpublicfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.flickrpublicfeed.R
import com.example.flickrpublicfeed.Record

import com.squareup.picasso.Picasso

class Picture : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_picture, container, false)

        val record: Record = arguments?.getParcelable(Split.FPF_KEY_PHOTO_ITEM)!!
        val imageView: ImageView = view.findViewById(R.id.pictureBig)
        Picasso.get()
            .load(record.imageURL)
            .into(imageView)

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(record: Record) =
            Picture().apply {
                arguments = Bundle().apply {
                    putParcelable(Split.FPF_KEY_PHOTO_ITEM, record)
                }
            }
    }
}