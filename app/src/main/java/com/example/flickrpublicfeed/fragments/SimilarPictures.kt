package com.example.flickrpublicfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrpublicfeed.R
import com.example.flickrpublicfeed.Record



class SimilarPictures : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_similar_pictures, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val similarPictures: ArrayList<String> = arguments?.getStringArrayList(Split.FPF_KEY_RELEVANT_PHOTOS_URLS)!!

        view.findViewById<RecyclerView>(R.id.similarPictures).apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = SimilarAdapter(similarPictures)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(item: Record, similarPictures: ArrayList<String>) =
            SimilarPictures().apply {
                arguments = Bundle().apply {
                    putParcelable(Split.FPF_KEY_PHOTO_ITEM, item)
                    putStringArrayList(Split.FPF_KEY_RELEVANT_PHOTOS_URLS, similarPictures)
                }
            }
    }
}