package com.example_info.rentease.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example_info.rentease.databinding.SingleMiniImageBinding

class MiniImageAdapter(
    private val onDelete: (Uri) -> Unit,
) :
    ListAdapter<Uri, MiniImageAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = SingleMiniImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onDelete = onDelete,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: SingleMiniImageBinding,
        private val onDelete: (Uri) -> Unit,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(item: Uri) {
            binding.btnDelete.setOnClickListener {
                onDelete(item)
            }
            Glide
                .with(binding.root.context)
                .load(item)
                .into(binding.ivPoster)
        }

    }
}
