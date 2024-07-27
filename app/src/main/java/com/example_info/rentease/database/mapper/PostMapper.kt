package com.example_info.rentease.database.mapper

import com.example_info.rentease.database.entity.PropertyEntity
import com.example_info.rentease.model.RentDetailItem
import com.example_info.rentease.model.RentPreviewItem

fun RentDetailItem.toEntity() = PropertyEntity(
    id = id,
    previewImage = previewImage,
    region = region,
    quarter = quarter,
    city = city,
    type = type,
    price = price,
    creatorId = creatorId,
    creatorName = creatorName,
    date = date,
    images = images,
    facilityList = facilityList,
    description = description,
    totalRating = totalRating,
    contactPhone = contactPhone,
    ratingList = ratingList,
    interestedList = interestedList,
)

fun PropertyEntity.toDomain(userId: String) = RentDetailItem(
    id = id,
    previewImage = previewImage,
    region = region,
    quarter = quarter,
    city = city,
    type = type,
    price = price,
    creatorId = creatorId,
    creatorName = creatorName,
    date = date,
    images = images,
    facilityList = facilityList,
    description = description,
    totalRating = totalRating,
    contactPhone = contactPhone,
    ratingList = ratingList,
    interestedList = interestedList,
    hasInterested = interestedList.contains(userId),
    hasRated = ratingList.contains(userId),
)

fun PropertyEntity.toPreview() = RentPreviewItem(
    id = id,
    previewImage = previewImage,
    region = region,
    quarter = quarter,
    city = city,
    type = type,
    price = price,
    creatorName = creatorName,
    date = date,
)
