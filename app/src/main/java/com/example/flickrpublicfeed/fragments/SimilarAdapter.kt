package com.example.flickrpublicfeed.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrpublicfeed.R
import com.squareup.picasso.Picasso

class SimilarAdapter(private val similarPictures: ArrayList<String>) :
    RecyclerView.Adapter<SimilarAdapter.SimilarPicturesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarPicturesViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.similar_record, parent, false)

        return SimilarPicturesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return similarPictures.size
    }

    override fun onBindViewHolder(holder: SimilarPicturesViewHolder, position: Int) {
        holder.bind(similarPictures[position])
    }

    class SimilarPicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val picture: ImageView = itemView.findViewById(R.id.similarPicture)

        fun bind(url: String) {
            Picasso.get()
                .load(url).placeholder(R.drawable.placeholder)
                .error(R.drawable.errorplaceholder)
                .into(picture)
        }
    }
}