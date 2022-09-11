package com.example.outdoorsy.model.meta

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    @SerializedName("total")
    @Expose
    var total: Int
) : Parcelable