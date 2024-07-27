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
    val totalRating: Long,
    val contactPhone: String,
    val ratingList: List<String>,
    val interestedList: List<String>,
    // personal fields
    val hasInterested: Boolean = false,
    val hasRated: Boolean = false,
) {
    val totalRatingCount: Int
        get() = ratingList.size

    val interestedCount: Int
        get() = interestedList.size
}
