package com.example_info.rentease.database.callback

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example_info.rentease.database.RentEaseDB
import com.example_info.rentease.database.entity.PropertyEntity
import com.example_info.rentease.database.mapper.toEntity
import com.example_info.rentease.mock.sampleRentDetailItems
import com.example_info.rentease.util.constants.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyDatabaseCallback(
    private val context: Context,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Populate database in the background
        CoroutineScope(Dispatchers.IO).launch {
            populateDatabase(context, sampleRentDetailItems.map { it.toEntity() })
        }
    }

    private suspend fun populateDatabase(context: Context, data: List<PropertyEntity>) {
        // Get the database and DAO instance
        val db = Room.databaseBuilder(
            context,
            RentEaseDB::class.java,
            AppConstants.DB_NAME
        ).build()

        val propertyDao = db.propertyDao()
        // Insert preloaded data
        propertyDao.insertAll(data)
    }
}
