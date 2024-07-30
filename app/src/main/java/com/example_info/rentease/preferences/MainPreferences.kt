package com.example_info.rentease.preferences

import android.content.Context

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

class MainPreferences(context: Context) {

    var currentUserId: Long
        get() = preferences.getLong(KEY_CURRENT_USER_ID, -1)
        set(value) {
            preferences.edit().putLong(KEY_CURRENT_USER_ID, value).apply()
        }

    val isLoggedIn: Boolean
        get() = currentUserId > -1

    fun logout() {
        currentUserId = -1
    }

    private val preferences by lazy {
        context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
    }

    companion object {
        private const val MY_PREF = "RentEasePreferences"

        private const val KEY_CURRENT_USER_ID = "IS_CURRENT_USER_ID"
    }

}
