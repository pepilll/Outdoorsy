package com.example.outdoorsy.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrimaryImage(
    @SerializedName("data")
    @Expose
    var imageData: ImageData?
) : Parcelable