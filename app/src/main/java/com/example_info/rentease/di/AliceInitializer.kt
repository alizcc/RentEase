package com.example_info.rentease.di

import android.content.Context
import androidx.room.Room
import com.example_info.rentease.database.RentEaseDB
import com.example_info.rentease.database.callback.PropertyDatabaseCallback
import com.example_info.rentease.preferences.MainPreferences
import com.example_info.rentease.util.constants.AppConstants

/**
 * Easy to use the database and preferences class
 */
object AliceInitializer {

    private var _database: RentEaseDB? = null
    private var _preference: MainPreferences? = null

    fun getMainPreferences(context: Context): MainPreferences {
        _preference?.let { return it }
        _preference = MainPreferences(context)
        return _preference!!
    }

    fun getDatabase(context: Context): RentEaseDB {
        _database?.let { return it }
        _database = Room.databaseBuilder(context, RentEaseDB::class.java, AppConstants.DB_NAME)
            .allowMainThreadQueries()
            .addCallback(PropertyDatabaseCallback(context))
//            .fallbackToDestructiveMigration()
            .build()
        return _database!!
    }

    fun destroy() {
        _database = null
        _preference = null
    }

}
