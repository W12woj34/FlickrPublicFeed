package com.example.flickrpublicfeed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Record(
    val imageURL: String, val name: String, val date: String) : Parcelable



