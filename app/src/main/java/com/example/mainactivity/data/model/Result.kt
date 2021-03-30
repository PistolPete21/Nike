package com.example.mainactivity.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val title: String = "",
    val type: String = "",
    val url: String = "",
    val published_date:String = "",
    val media: List<Media> = listOf(),
) : Parcelable