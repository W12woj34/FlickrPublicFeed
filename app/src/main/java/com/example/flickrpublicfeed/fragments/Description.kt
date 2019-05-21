package com.example.flickrpublicfeed.fragments



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.flickrpublicfeed.R
import com.example.flickrpublicfeed.Record


class Description : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_description, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val record= arguments?.getParcelable(FPF_KEY_RECORD_DESCRIPTION ) as Record
        view.findViewById<TextView>(R.id.NameSetView).text = record.name
        view.findViewById<TextView>(R.id.DateSetView).text = record.date
        view.findViewById<TextView>(R.id.TagsSetView).text = record.tags.joinToString(", ")

    }

    companion object {

        private const val FPF_KEY_RECORD_DESCRIPTION = "FPF_KEY_RECORD_DESCRIPTION"

        @JvmStatic
        fun newInstance(item: Record) =
            Description().apply {
                arguments = Bundle().apply {
                    putParcelable(FPF_KEY_RECORD_DESCRIPTION , item)
                }
            }
    }
}