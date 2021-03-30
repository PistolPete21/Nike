package com.example.mainactivity.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
    val type: String = "",
    val subtype: String = "",
    @SerializedName(value = "media-metadata")
    val media_metadata: List<MediaImage> = listOf(),
) : Parcelable