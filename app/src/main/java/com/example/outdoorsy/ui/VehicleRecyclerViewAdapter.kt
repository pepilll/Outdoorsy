package com.example.outdoorsy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.outdoorsy.R
import com.example.outdoorsy.databinding.CardItemRecyclerViewBinding
import com.example.outdoorsy.model.data.Data
import com.example.outdoorsy.model.includedData.IncludedDataObjects

internal class VehicleRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vehicleList: List<Data> = ArrayList()
    private lateinit var includedData: ArrayList<IncludedDataObjects>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardItemRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VehicleViewHolder -> holder.bind(vehicleList[position], includedData)
        }
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    fun submitData(vehicleDataList: ArrayList<Data>, includedData: ArrayList<IncludedDataObjects>) {
        vehicleList = vehicleDataList
        this.includedData = includedData
    }

    class VehicleViewHolder constructor(
        binding: CardItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.searchEditTextView
        private val image = binding.vehicleImageView

        fun bind(vehicleData: Data, includedData: ArrayList<IncludedDataObjects>) {
            title.text = vehicleData.attributes?.name
            val imageId = vehicleData.relationships?.primaryImage?.imageData?.id

            var imageUrl: String? = ""

            for (i in includedData) {
                if (imageId == i.id && imageId!=null) {
                    imageUrl = i.attributes?.url
                }
            }

            if (imageUrl.isNullOrEmpty()) {
                image.setImageResource(R.drawable.ic_launcher_background)
            } else {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(imageUrl)
                    .centerCrop()
                    .into(image)
            }
        }
    }
}
