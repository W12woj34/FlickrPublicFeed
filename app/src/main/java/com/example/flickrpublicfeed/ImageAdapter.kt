package com.example.flickrpublicfeed


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.main_record.view.*
import java.util.*

class ImageAdapter(private val records: ArrayList<Record>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(com.example.flickrpublicfeed.R.layout.main_record, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        val picasso = Picasso.get()

        holder.imgName.text = record.name
        holder.date.text = record.date

        picasso
            .load(record.imageURL)
            .placeholder(R.drawable.placeholder).error(R.drawable.errorplaceholder)
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    holder.image.setImageDrawable(placeHolderDrawable)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    holder.image.setImageDrawable(errorDrawable)
                }

                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                    holder.image.setImageBitmap(bitmap)
                    getTags(bitmap, holder, record)
                }
            })

        setImageClick(holder, record)

    }

    private fun setImageClick(holder: ViewHolder, record:Record) {

        holder.image.setOnClickListener {
            val context = holder.image.context
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.FPF_KEY_RECORD, record)
            intent.putStringArrayListExtra(
                DetailsActivity.FPF_KEY_SIMILAR_PHOTOS,
                getSimilarPhotos(record)
            )
            startActivity(context, intent, Bundle.EMPTY)
        }
    }

    fun deleteItem(position: Int) {
        records.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getTags(bitmap: Bitmap, holder: ViewHolder, record: Record) {
        val visionImg = FirebaseVisionImage.fromBitmap(bitmap)
        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
        labeler.processImage(visionImg)
            .addOnSuccessListener { tagsProvided ->
                holder.tags.text = tagsProvided.map { it.text }.take(3).joinToString(", ")
                record.tags.clear()
                record.tags.addAll(tagsProvided.map { it.text })
            }
            .addOnFailureListener { e ->
                Log.wtf("LAB", e)
            }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.mainImage!!
        val imgName = itemView.mainName!!
        val tags = itemView.mainTags!!
        val date = itemView.mainDate!!
    }

    private fun getSimilarPhotos(record: Record): ArrayList<String> {
        val similarPictures = records.filter { item ->
            item.tags.any { tag -> record.tags.contains(tag) } && item != record
        }

        return similarPictures.take(6).map { item -> item.imageURL } as ArrayList<String>
    }

}