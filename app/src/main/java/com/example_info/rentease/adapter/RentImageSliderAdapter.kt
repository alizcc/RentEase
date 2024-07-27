package com.example_info.rentease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example_info.rentease.databinding.SingleImageSliderBinding

class RentImageSliderAdapter(
    private val items: List<String>,
) : RecyclerView.Adapter<RentImageSliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = SingleImageSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(
        private val binding: SingleImageSliderBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(item: String) {
            val showImage = item.isNotBlank()
            binding.tvNoImage.isVisible = !showImage
            binding.ivNoImage.isVisible = !showImage
            binding.ivPoster.isVisible = showImage
            if (showImage) {
                Glide
                    .with(binding.root.context)
                    .load(item)
                    .into(binding.ivPoster)
            }
        }

    }
}
