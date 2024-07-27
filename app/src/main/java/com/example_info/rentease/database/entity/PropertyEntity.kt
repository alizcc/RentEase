package com.example_info.rentease.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property_entity")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val previewImage: String,
    val region: String,
    val quarter: String,
    val city: String,
    val type: String,
    val price: Long,
    val creatorId: String,
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
) {
}
