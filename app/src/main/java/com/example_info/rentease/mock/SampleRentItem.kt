package com.example_info.rentease.mock

import com.example_info.rentease.model.RentPreviewItem

fun getSampleTabTypes(size: Int) = sampleRentTypes.subList(0, size)

fun getSampleRentPreviewItems(size: Int, type: String? = null) =
    if (type == null) {
        sampleRentPreviewItems.subList(0, size)
    } else {
        val filtered = sampleRentPreviewItems.filter {
            it.type.equals(type, true)
        }
        val newSize = size.takeIf { it <= filtered.size } ?: filtered.size
        filtered.subList(0, newSize)
    }

private val sampleRentTypes = listOf(
    "Apartment", "Condo", "Studio", "House", "Bungalow", "Townhouse"
)
private val sampleRentPreviewItems = listOf(
    RentPreviewItem(
        id = 1L,
        previewImage = "https://images.unsplash.com/photo-1568605114967-8130f3a36994",
        region = "California",
        quarter = "Q1",
        city = "Los Angeles",
        type = "Apartment",
        price = 250000L,
        creatorName = "John Doe",
        date = "2024-01-15"
    ),
    RentPreviewItem(
        id = 2L,
        previewImage = "https://images.unsplash.com/photo-1600585154340-be6161c4d6df",
        region = "California",
        quarter = "Q1",
        city = "San Francisco",
        type = "Condo",
        price = 320000L,
        creatorName = "Jane Smith",
        date = "2024-01-10"
    ),
    RentPreviewItem(
        id = 3L,
        previewImage = "https://images.unsplash.com/photo-1560448204-e3a4e7d9d2b0",
        region = "New York",
        quarter = "Q1",
        city = "New York City",
        type = "Apartment",
        price = 400000L,
        creatorName = "Michael Johnson",
        date = "2024-01-20"
    ),
    RentPreviewItem(
        id = 4L,
        previewImage = "https://images.unsplash.com/photo-1512917774080-9991f1c4c750",
        region = "Illinois",
        quarter = "Q1",
        city = "Chicago",
        type = "Apartment",
        price = 200000L,
        creatorName = "Emily Davis",
        date = "2024-01-05"
    ),
    RentPreviewItem(
        id = 5L,
        previewImage = "https://images.unsplash.com/photo-1518733057094-95b53151d5a5",
        region = "Texas",
        quarter = "Q1",
        city = "Houston",
        type = "House",
        price = 180000L,
        creatorName = "Chris Brown",
        date = "2024-01-25"
    ),
    RentPreviewItem(
        id = 6L,
        previewImage = "https://images.unsplash.com/photo-1521782462921-d7c1106d6237",
        region = "Nevada",
        quarter = "Q1",
        city = "Las Vegas",
        type = "Apartment",
        price = 150000L,
        creatorName = "Patricia Wilson",
        date = "2024-01-18"
    ),
    RentPreviewItem(
        id = 7L,
        previewImage = "https://images.unsplash.com/photo-1570129477492-45c003edd2be",
        region = "Florida",
        quarter = "Q1",
        city = "Miami",
        type = "Condo",
        price = 280000L,
        creatorName = "James Anderson",
        date = "2024-01-12"
    ),
    RentPreviewItem(
        id = 8L,
        previewImage = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb",
        region = "Washington",
        quarter = "Q1",
        city = "Seattle",
        type = "Apartment",
        price = 270000L,
        creatorName = "Linda Martinez",
        date = "2024-01-17"
    ),
    RentPreviewItem(
        id = 9L,
        previewImage = "https://images.unsplash.com/photo-1580587771525-78b9dba3b914",
        region = "Colorado",
        quarter = "Q1",
        city = "Denver",
        type = "House",
        price = 220000L,
        creatorName = "Robert Lee",
        date = "2024-01-22"
    ),
    RentPreviewItem(
        id = 10L,
        previewImage = "https://images.unsplash.com/photo-1580587771525-b93f9b4c92d3",
        region = "Arizona",
        quarter = "Q1",
        city = "Phoenix",
        type = "Apartment",
        price = 140000L,
        creatorName = "Jennifer Thomas",
        date = "2024-01-08"
    ),
    RentPreviewItem(
        id = 11L,
        previewImage = "https://images.unsplash.com/photo-1598928506312-88e4c4ce03c8",
        region = "Ohio",
        quarter = "Q1",
        city = "Columbus",
        type = "House",
        price = 170000L,
        creatorName = "William Jackson",
        date = "2024-01-14"
    ),
    RentPreviewItem(
        id = 12L,
        previewImage = "https://images.unsplash.com/photo-1574175416935-12cd2d52bdb9",
        region = "Georgia",
        quarter = "Q1",
        city = "Atlanta",
        type = "Condo",
        price = 260000L,
        creatorName = "Elizabeth White",
        date = "2024-01-23"
    ),
    RentPreviewItem(
        id = 13L,
        previewImage = "https://images.unsplash.com/photo-1599423300746-b62533397364",
        region = "North Carolina",
        quarter = "Q1",
        city = "Charlotte",
        type = "Apartment",
        price = 190000L,
        creatorName = "David Harris",
        date = "2024-01-11"
    ),
    RentPreviewItem(
        id = 14L,
        previewImage = "https://images.unsplash.com/photo-1600607683923-0e973a6b1b61",
        region = "Virginia",
        quarter = "Q1",
        city = "Richmond",
        type = "House",
        price = 160000L,
        creatorName = "Barbara Clark",
        date = "2024-01-16"
    ),
    RentPreviewItem(
        id = 15L,
        previewImage = "https://images.unsplash.com/photo-1560185008-5f0bbf3d9c74",
        region = "Oregon",
        quarter = "Q1",
        city = "Portland",
        type = "Condo",
        price = 240000L,
        creatorName = "Richard Lewis",
        date = "2024-01-07"
    ),
    RentPreviewItem(
        id = 16L,
        previewImage = "https://images.unsplash.com/photo-1599423466075-1c3b49d36220",
        region = "Minnesota",
        quarter = "Q1",
        city = "Minneapolis",
        type = "Apartment",
        price = 210000L,
        creatorName = "Susan Walker",
        date = "2024-01-19"
    ),
    RentPreviewItem(
        id = 17L,
        previewImage = "https://images.unsplash.com/photo-1600585154195-26d5dd14db82",
        region = "Michigan",
        quarter = "Q1",
        city = "Detroit",
        type = "House",
        price = 130000L,
        creatorName = "Joseph Hall",
        date = "2024-01-24"
    ),
    RentPreviewItem(
        id = 18L,
        previewImage = "https://images.unsplash.com/photo-1600585153943-6ca1a1103a92",
        region = "Massachusetts",
        quarter = "Q1",
        city = "Boston",
        type = "Apartment",
        price = 300000L,
        creatorName = "Karen Allen",
        date = "2024-01-13"
    ),
    RentPreviewItem(
        id = 19L,
        previewImage = "https://images.unsplash.com/photo-1600585160350-3b9d68e80b48",
        region = "Pennsylvania",
        quarter = "Q1",
        city = "Philadelphia",
        type = "Condo",
        price = 290000L,
        creatorName = "Charles Young",
        date = "2024-01-21"
    ),
    RentPreviewItem(
        id = 20L,
        previewImage = "https://images.unsplash.com/photo-1600585154483-3a7c968f43b3",
        region = "Maryland",
        quarter = "Q1",
        city = "Baltimore",
        type = "House",
        price = 250000L,
        creatorName = "Nancy King",
        date = "2024-01-09"
    )
)
