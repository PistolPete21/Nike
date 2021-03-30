package com.example.mainactivity.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaImage (
    val url: String = "",
    val height: Int = 0,
    val width: Int = 0,
) : Parcelable