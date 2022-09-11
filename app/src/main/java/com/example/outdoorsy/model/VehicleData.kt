package com.example.outdoorsy.model

import android.os.Parcelable
import com.example.outdoorsy.model.data.Data
import com.example.outdoorsy.model.includedData.IncludedDataObjects
import com.example.outdoorsy.model.meta.Meta
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleData(
    @SerializedName("data")
    @Expose
    var data: List<Data>,
    @SerializedName("included")
    @Expose
    var included: List<IncludedDataObjects>?,
    @SerializedName("meta")
    @Expose
    var meta: Meta
) : Parcelable