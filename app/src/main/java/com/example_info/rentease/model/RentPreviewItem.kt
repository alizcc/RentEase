package com.example_info.rentease.model

data class RentPreviewItem(
    val id: Long,
    val previewImage: String,
    val region: String,
    val quarter: String,
    val city: String,
    val type: String,
    val price: Long,
    val creatorName: String,
    val date: String,
)
