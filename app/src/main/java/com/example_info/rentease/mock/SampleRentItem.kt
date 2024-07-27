package com.example_info.rentease.mock

import com.example_info.rentease.model.RentDetailItem
import com.example_info.rentease.model.RentPreviewItem

fun getSampleTabTypes(size: Int? = null) =
    sampleRentTypes.subList(0, size ?: (sampleRentTypes.size - 1))

fun getSampleRentPreviewItems(size: Int? = null, type: String? = null) =
    if (size == null) {
        sampleRentPreviewItems
    } else if (type == null) {
        sampleRentPreviewItems.subList(0, size)
    } else {
        val filtered = sampleRentPreviewItems.filter {
            it.type.equals(type, true)
        }
        val newSize = size.takeIf { it <= filtered.size } ?: filtered.size
        filtered.subList(0, newSize)
    }

private val sampleRentTypes = listOf(
    "apartment" to "တိုက်ခန်း", "condo" to "ကွန်ဒို",
    "minicondo" to "မီနီကွန်ဒို", "bungalow" to "လုံးချင်းအိမ်",
    "field" to "မြေကွက်/ ခြံ", "hostel" to "အဆောင်"
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
    interestedCount = 5,
    totalRating = 10,
    totalRatingCount = 3,
    contactPhone = "+95984849984",
    hasInterested = false,
    hasRated = false,
)

private val sampleRentPreviewItems = listOf(
    RentPreviewItem(
        id = 1L,
        previewImage = "https://na.rdcpix.com/63bc3db5f65286a1fb4e2b1af9e14591w-c1980730521srd_q80.jpg",
        region = "အလုံမြို့နယ်",
        quarter = "၁ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "condo",
        price = 1500000,
        creatorName = "John Doe",
        date = "2024-01-15"
    ),
    RentPreviewItem(
        id = 2L,
        previewImage = "https://www.montgomeryhomes.com.au/wp-content/uploads/2022/05/Montgomery-Homes-Display-Home-Bellevue-288-facade.jpg",
        region = "ဗဟန်းမြို့နယ်",
        quarter = "၁၂ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "bungalow",
        price = 3000000,
        creatorName = "Jane Smith",
        date = "2024-01-10"
    ),
    RentPreviewItem(
        id = 3L,
        previewImage = "https://www.bankrate.com/2017/10/17165457/what-to-know-before-you-buy-a-condo.jpg",
        region = "ဗိုလ်တထောင်မြို့နယ်",
        quarter = "၅ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "condo",
        price = 2000000,
        creatorName = "Michael Johnson",
        date = "2024-01-20"
    ),
    RentPreviewItem(
        id = 4L,
        previewImage = "https://apprivals-insecure.b-cdn.net/applications/npnews-mm/news_articles/62722733b5990c0783299405/banner-62722733b5990c0783299405-x800.png",
        region = "လှိုင်သာယာမြို့နယ်",
        quarter = "၈ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "field",
        price = 200000,
        creatorName = "Emily Davis",
        date = "2024-01-05"
    ),
    RentPreviewItem(
        id = 5L,
        previewImage = "https://foyr.com/learn/wp-content/uploads/2022/06/types-of-house-styles-and-homes.jpg",
        region = "လှိုင်မြို့နယ်",
        quarter = "၁၀ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "bungalow",
        price = 2700000,
        creatorName = "Chris Brown",
        date = "2024-01-25"
    ),
    RentPreviewItem(
        id = 6L,
        previewImage = "https://www.kayak.com/news/wp-content/uploads/sites/19/2023/08/THEME_HOTEL_HOSTEL_BEDROOM-shutterstock-portfolio_1708374559.jpg",
        region = "ကမာရွတ်မြို့နယ်",
        quarter = "၃ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "hostel",
        price = 120000,
        creatorName = "Patricia Wilson",
        date = "2024-01-18"
    ),
    RentPreviewItem(
        id = 7L,
        previewImage = "https://images.livspace-cdn.com/plain/https://jumanji.livspace-cdn.com/magazine/wp-content/uploads/sites/3/2021/07/22060733/cover-1.png",
        region = "အင်းစိန်မြို့နယ်",
        quarter = "၁၅ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "minicondo",
        price = 850000,
        creatorName = "James Anderson",
        date = "2024-01-12"
    ),
    RentPreviewItem(
        id = 8L,
        previewImage = "https://res.cloudinary.com/hostelling-internation/image/upload/f_auto,q_auto/v1554994870/tgocza9viyqkxltfd7bh.jpg",
        region = "ကြည့်မြင်တိုင်မြို့နယ်",
        quarter = "၉ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "hostel",
        price = 70000,
        creatorName = "Linda Martinez",
        date = "2024-01-17"
    ),
    RentPreviewItem(
        id = 9L,
        previewImage = "https://media-cdn.tripadvisor.com/media/vr-splice-j/09/d4/01/04.jpg",
        region = "မရမ်းကုန်းမြို့နယ်",
        quarter = "၂ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "apartment",
        price = 320000,
        creatorName = "Robert Lee",
        date = "2024-01-22"
    ),
    RentPreviewItem(
        id = 10L,
        previewImage = "https://propertiesinyangon.com/wp-content/uploads/2019/11/6E17A8C7-C8EB-4500-A2A1-36A9C7D051A6-7097-000003814D4BCB81-1024x576.jpg",
        region = "မြောက်ဥက္ကလာမြို့နယ်",
        quarter = "၁၁ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "apartment",
        price = 290000,
        creatorName = "Jennifer Thomas",
        date = "2024-01-08"
    ),

    RentPreviewItem(
        id = 11L,
        previewImage = "https://static3.mansionglobal.com/production/media/article-images/c78f57d9ad346a9fb7128b708699fb85/large_251-First-Street.jpg",
        region = "ပုဇွန်တောင်မြို့နယ်",
        quarter = "၁ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "condo",
        price = 2700000,
        creatorName = "William Jackson",
        date = "2024-01-14"
    ),
    RentPreviewItem(
        id = 12L,
        previewImage = "https://cdn.fazwaz.com/nw/fOfcnsU3z9h-pi74wdfmWmTaYjA/790x400/unit/1092366/1.jpg",
        region = "ရွှေပြည်သာမြို့နယ်",
        quarter = "၄ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "field",
        price = 160000,
        creatorName = "Elizabeth White",
        date = "2024-01-23"
    ),
    RentPreviewItem(
        id = 13L,
        previewImage = "https://propertychannel.com.mm/wp-content/uploads/2022/09/307465141_622324769606375_6156960848365898588_n.jpg",
        region = "တောင်ဥက္ကလာမြို့နယ်",
        quarter = "၇ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "field",
        price = 190000,
        creatorName = "David Harris",
        date = "2024-01-11"
    ),
    RentPreviewItem(
        id = 14L,
        previewImage = "https://content.jdmagicbox.com/comp/ghaziabad/w4/011pxx11.xx11.191217190807.e5w4/catalogue/rpn-boy-s-hostel-dasna-ghaziabad-hostels-adyayiouqi.jpg",
        region = "တာမွေမြို့နယ်",
        quarter = "၁၃ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "hostel",
        price = 60000,
        creatorName = "Barbara Clark",
        date = "2024-01-16"
    ),
    RentPreviewItem(
        id = 15L,
        previewImage = "https://galathome.com/wp-content/uploads/2020/03/condo-living-room-with-wall-shelves-and-custom-media-cabinet2-1024x683.jpg",
        region = "သာကေတမြို့နယ်",
        quarter = "၂ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "minicondo",
        price = 480000,
        creatorName = "Richard Lewis",
        date = "2024-01-07"
    ),
    RentPreviewItem(
        id = 16L,
        previewImage = "https://media.homeanddecor.com.sg/public/2016/01/bt20160116scspace16jl5v2065614-1.jpg",
        region = "ရန်ကင်းမြို့နယ်",
        quarter = "၃ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "minicondo",
        price = 510000,
        creatorName = "Susan Walker",
        date = "2024-01-19"
    ),
    RentPreviewItem(
        id = 17L,
        previewImage = "https://img.onmanorama.com/content/dam/mm/en/lifestyle/decor/images/2023/6/1/house-middleclass.jpg",
        region = "သင်္ဃန်းကျွန်းမြို့နယ်",
        quarter = "၆ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "bungalow",
        price = 3300000,
        creatorName = "Joseph Hall",
        date = "2024-01-24"
    ),
    RentPreviewItem(
        id = 18L,
        previewImage = "https://img.onmanorama.com/content/dam/mm/en/lifestyle/decor/images/2023/6/1/house-middleclass.jpg",
        region = "မင်္ဂလာဒုံမြို့နယ်",
        quarter = "၈ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "bungalow",
        price = 2950000,
        creatorName = "Karen Allen",
        date = "2024-01-13"
    ),
    RentPreviewItem(
        id = 19L,
        previewImage = "https://images.squarespace-cdn.com/content/v1/6270dcb52a53a65bc96c6dae/ee43aff3-f27d-409f-b5be-a53dd7f494e0/image-asset.jpeg",
        region = "စမ်းချောင်းမြို့နယ်",
        quarter = "၅ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "apartment",
        price = 390000,
        creatorName = "Charles Young",
        date = "2024-01-21"
    ),
    RentPreviewItem(
        id = 20L,
        previewImage = "https://www.myanmar-realestate.com/wp-content/uploads/2022/03/acm-01-1.jpg",
        region = "မင်္ဂလာတောင်ညွှန့်မြို့နယ်",
        quarter = "၄ရပ်ကွက်",
        city = "ရန်ကုန်တိုင်းဒေသကြီး",
        type = "minicondo",
        price = 760000,
        creatorName = "Nancy King",
        date = "2024-01-09"
    )
)
