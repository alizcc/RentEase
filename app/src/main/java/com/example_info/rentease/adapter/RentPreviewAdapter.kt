package com.example_info.rentease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example_info.rentease.databinding.SingleRentPreviewItemBinding
import com.example_info.rentease.model.RentPreviewItem

class RentPreviewAdapter(
    private val onClick: (RentPreviewItem) -> Unit,
) : ListAdapter<RentPreviewItem, RentPreviewAdapter.ViewHolder>(RentPreviewDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = SingleRentPreviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: SingleRentPreviewItemBinding,
        private val onClick: (RentPreviewItem) -> Unit,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        private fun getReadablePrice(price: Long): String {
            return if (price > 1000) {
                "$price"
            } else {
                "${price / 1000} K"
            }
        }

        fun bind(item: RentPreviewItem) {
            Glide
                .with(binding.root.context)
                .load(item.previewImage)
                .into(binding.ivPoster)
            binding.tvTitle.text = "${item.quarter}၊ ${item.region}"
            binding.tvDescription.text = item.city
            binding.tvPrice.text = getReadablePrice(item.price)
            binding.root.setOnClickListener {
                onClick(item)
            }
        }

    }
}
