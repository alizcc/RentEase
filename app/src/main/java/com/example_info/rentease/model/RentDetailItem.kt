package com.example_info.rentease.model

data class RentDetailItem(
    val id: Long,
    val previewImage: String,
    val region: String,
    val quarter: String,
    val city: String,
    val type: String,
    val price: Long,
    val creatorName: String,
    val date: String,
    // new attributes
    val images: List<String>,
    val facilityList: List<String>,
    val description: String,
    val interestedCount: Long,
    val totalRating: Long,
    val totalRatingCount: Long,
    val contactPhone: String,
    // personal fields
    val hasInterested: Boolean,
    val hasRated: Boolean,
)
