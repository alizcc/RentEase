package com.example_info.rentease.util.helper

import android.net.Uri

fun tryParseToUri(uriString: String?): Uri? {
    return try {
        Uri.parse(uriString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
