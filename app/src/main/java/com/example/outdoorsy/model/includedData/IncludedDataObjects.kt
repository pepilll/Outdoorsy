package com.example.outdoorsy.model.includedData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IncludedDataObjects(
    @SerializedName("id")
    @Expose
    var id: Int?,
    @SerializedName("attributes")
    @Expose
    var attributes: Attributes?
) : Parcelable