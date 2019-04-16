package com.example.flickrpublicfeed

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso

class ImageAdapter(private val items: ArrayList<Record>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_record, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = items[position]


        holder.date.text = record.date
        holder.name.text = record.name
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(record.imageURL).placeholder(R.drawable.placeholder).error(R.drawable.errorplaceholder)
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    holder.image.setImageBitmap(bitmap)
                    getTags(bitmap, holder.tags)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    holder.image.setImageDrawable(placeHolderDrawable)
                }
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    holder.image.setImageDrawable(errorDrawable)
                }
            })


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView = itemView.findViewById(R.id.mainImage)
        val date: TextView = itemView.findViewById(R.id.mainDate)
        val name: TextView = itemView.findViewById(R.id.mainName)
        val tags: TextView = itemView.findViewById(R.id.mainTags)

    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getTags(bitmap: Bitmap, view: TextView) {

        bitmap.apply {
            val vision = FirebaseVisionImage.fromBitmap(this)
            val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
            labeler.processImage(vision)
                .addOnSuccessListener {
                    val tags = it.map { it.text }
                    setTags(tags.take(3), view)
                    Log.d("LAB", it.map { it.text }.joinToString(" "))
                }
                .addOnFailureListener {
                    Log.wtf("LAB", it.message)
                }
        }
    }

    private fun setTags(tags: List<String>, view: TextView){
        val result = tags.joinToString(", ")
        view.text = result
    }
}
