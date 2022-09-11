package com.example.outdoorsy.model.includedData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attributes(
    @SerializedName("url")
    @Expose
    var url: String?
) : Parcelable