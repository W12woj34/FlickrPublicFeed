package com.example.flickrpublicfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.tags.text = getTags(record.tags)
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(record.imageURL).placeholder(R.drawable.placeholder).error(R.drawable.errorplaceholder).into(holder.image)

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

    private fun getTags(tags: ArrayList<String>): CharSequence? {
        var result = ""
        return when {
            tags.size == 0 -> result
            tags.size == 1 -> {
                result = tags[0]
                result
            }
            tags.size == 2 -> {
                result = tags[0] + "; " + tags[1]
                result
            }
            else -> {
                result = tags[0] + "; " + tags[1] + "; " + tags[2]
                result
            }
        }

    }
}
