package com.example_info.rentease.mock

import com.example_info.rentease.model.RentDetailItem

private val sampleRentDetailItems = listOf(
    RentDetailItem(
        id = 1L,
        previewImage = "https://na.rdcpix.com/63bc3db5f65286a1fb4e2b1af9e14591w-c1980730521srd_q80.jpg",
        region = "အလုံမြို့နယ်",
        quarter = "၁ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "condo",
        price = 1500000,
        creatorName = "John Doe",
        date = "2024-01-15",
        images = listOf(
            "https://na.rdcpix.com/63bc3db5f65286a1fb4e2b1af9e14591w-c1980730521srd_q80.jpg",
            "https://www.montgomeryhomes.com.au/wp-content/uploads/2022/05/Montgomery-Homes-Display-Home-Bellevue-288-facade.jpg",
            "https://www.bankrate.com/2017/10/17165457/what-to-know-before-you-buy-a-condo.jpg"
        ),
        facilityList = listOf(
            "ရေချိုးခန်း", "အိမ်သာ", "Master 2 Bedrooms", "ဂျင်", "ရေကူးကန်", "ရေချိုးဇလား"
        ),
        description = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

        Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra, est eros bibendum elit, nec luctus magna felis sollicitudin mauris. Integer in mauris eu nibh euismod gravida. Duis ac tellus et risus vulputate vehicula. Donec lobortis risus a elit. Etiam tempor. Ut ullamcorper, ligula eu tempor congue, eros est euismod turpis, id tincidunt sapien risus a quam. Maecenas fermentum consequat mi. Donec fermentum. Pellentesque malesuada nulla a mi. Duis sapien sem, aliquet nec, commodo eget, consequat quis, neque. Aliquam faucibus, elit ut dictum aliquet, felis nisl adipiscing sapien, sed malesuada diam lacus eget erat.
    """.trimIndent(),
        totalRating = 10,
        ratingList = listOf("", "", ""),
        interestedList = listOf("", "", "", "", ""),
        contactPhone = "+95984849984",
    ),
)

val sampleRentDetailItem = RentDetailItem(
    id = 1L,
    previewImage = "https://na.rdcpix.com/63bc3db5f65286a1fb4e2b1af9e14591w-c1980730521srd_q80.jpg",
    region = "အလုံမြို့နယ်",
    quarter = "၁ရပ်ကွက်",
    city = "ရန်ကုန်တိုင်းဒေသကြီး",
    type = "condo",
    price = 1500000,
    creatorName = "John Doe",
    date = "2024-01-15",
    images = listOf(
        "https://na.rdcpix.com/63bc3db5f65286a1fb4e2b1af9e14591w-c1980730521srd_q80.jpg",
        "https://www.montgomeryhomes.com.au/wp-content/uploads/2022/05/Montgomery-Homes-Display-Home-Bellevue-288-facade.jpg",
        "https://www.bankrate.com/2017/10/17165457/what-to-know-before-you-buy-a-condo.jpg"
    ),
    facilityList = listOf(
        "ရေချိုးခန်း", "အိမ်သာ", "Master 2 Bedrooms", "ဂျင်", "ရေကူးကန်", "ရေချိုးဇလား"
    ),
    description = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

        Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra, est eros bibendum elit, nec luctus magna felis sollicitudin mauris. Integer in mauris eu nibh euismod gravida. Duis ac tellus et risus vulputate vehicula. Donec lobortis risus a elit. Etiam tempor. Ut ullamcorper, ligula eu tempor congue, eros est euismod turpis, id tincidunt sapien risus a quam. Maecenas fermentum consequat mi. Donec fermentum. Pellentesque malesuada nulla a mi. Duis sapien sem, aliquet nec, commodo eget, consequat quis, neque. Aliquam faucibus, elit ut dictum aliquet, felis nisl adipiscing sapien, sed malesuada diam lacus eget erat.
    """.trimIndent(),
    totalRating = 10,
    ratingList = listOf("", "", ""),
    interestedList = listOf("", "", "", "", ""),
    contactPhone = "+95984849984",
)
