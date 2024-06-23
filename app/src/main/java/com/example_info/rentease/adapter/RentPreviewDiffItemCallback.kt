package com.example_info.rentease.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example_info.rentease.model.RentPreviewItem

object RentPreviewDiffItemCallback : DiffUtil.ItemCallback<RentPreviewItem>() {
    override fun areItemsTheSame(oldItem: RentPreviewItem, newItem: RentPreviewItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RentPreviewItem, newItem: RentPreviewItem): Boolean {
        return oldItem.id == newItem.id
    }
}
