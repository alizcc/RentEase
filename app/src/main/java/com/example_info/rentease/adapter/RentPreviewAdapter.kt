package com.example_info.rentease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example_info.rentease.databinding.SingleRentPreviewItemBinding
import com.example_info.rentease.model.RentPreviewItem
import com.example_info.rentease.util.helper.asCommaSeparated

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

        fun bind(item: RentPreviewItem) {
            val showImage = item.previewImage.isNotBlank()
            binding.tvNoImage.isVisible = !showImage
            binding.ivNoImage.isVisible = !showImage
            binding.ivPoster.isVisible = showImage
            if (showImage) {
                Glide
                    .with(binding.root.context)
                    .load(item.previewImage)
                    .into(binding.ivPoster)
            }
            binding.tvTitle.text = "${item.quarter}၊ ${item.region}"
            binding.tvDescription.text = item.city
            binding.tvPrice.text = item.price.asCommaSeparated
            binding.root.setOnClickListener {
                onClick(item)
            }
        }

    }
}
