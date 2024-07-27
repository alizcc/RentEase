package com.example_info.rentease.util.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {

    val todayFormattedDate: String
        get() {
            return try {
                val currentDate = Date()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateFormat.format(currentDate)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

}
