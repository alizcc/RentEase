package com.example_info.rentease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example_info.rentease.databinding.SingleRentPreviewSliderItemBinding
import com.example_info.rentease.mock.getSampleTabTypes
import com.example_info.rentease.model.RentPreviewItem
import com.example_info.rentease.util.helper.asCommaSeparated

class RentPreviewAutoSliderAdapter(
    private val items: List<RentPreviewItem>,
    private val onClick: (RentPreviewItem) -> Unit,
) : RecyclerView.Adapter<RentPreviewAutoSliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = SingleRentPreviewSliderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = onClick
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(
        private val binding: SingleRentPreviewSliderItemBinding,
        private val onClick: (RentPreviewItem) -> Unit,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private val propertyTypePairList = getSampleTabTypes()

        fun bind(item: RentPreviewItem) {
            Glide
                .with(binding.root.context)
                .load(item.previewImage)
                .into(binding.ivPoster)
            binding.tvRegion.text = item.quarter + ", " + item.region
            binding.tvPrice.text = item.price.asCommaSeparated
            binding.tvType.text = propertyTypePairList.find { it.first == item.type }?.second.orEmpty()
            binding.root.setOnClickListener {
                onClick(item)
            }
        }

    }
}
