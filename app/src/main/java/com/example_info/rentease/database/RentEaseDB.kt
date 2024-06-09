package com.example_info.rentease.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.entity.UserEntity

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

@Database(entities = [UserEntity::class], version = 1)
abstract class RentEaseDB : RoomDatabase() {

    abstract fun userDao(): UserDao

}
