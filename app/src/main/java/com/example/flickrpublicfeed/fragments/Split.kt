package com.example.flickrpublicfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flickrpublicfeed.R
import com.example.flickrpublicfeed.Record


class Split : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_split, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val record: Record = arguments?.getParcelable(FPF_KEY_PHOTO_ITEM)!!
        val similarPictures: ArrayList<String> = arguments?.getStringArrayList(FPF_KEY_RELEVANT_PHOTOS_URLS)!!

        childFragmentManager.beginTransaction().apply {
            replace(R.id.descriptionFragment, Description.newInstance(record))
            replace(R.id.similarPicturesFragment, SimilarPictures.newInstance(record, similarPictures))
            commit()
        }
    }

    companion object {
        const val FPF_KEY_PHOTO_ITEM = "FPF_KEY_PHOTO_ITEM"
        const val FPF_KEY_RELEVANT_PHOTOS_URLS = "FPF_KEY_RELEVANT_PHOTOS_URLS"

        @JvmStatic
        fun newInstance(item: Record, similarPictures: ArrayList<String>) =
            Split().apply {
                arguments = Bundle().apply {
                    putParcelable(FPF_KEY_PHOTO_ITEM, item)
                    putStringArrayList(FPF_KEY_RELEVANT_PHOTOS_URLS, similarPictures)
                }
            }
    }
}