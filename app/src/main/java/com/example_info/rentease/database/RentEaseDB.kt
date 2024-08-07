package com.example_info.rentease.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example_info.rentease.database.converter.Converters
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.entity.PropertyEntity
import com.example_info.rentease.database.entity.UserEntity

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

@Database(
    entities = [UserEntity::class, PropertyEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class RentEaseDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun propertyDao(): PropertyDao

}
