package com.example_info.rentease.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

@Entity(tableName = "user_entity")
data class UserEntity(
    val username: String,
    val password: String,
    val fullName: String,
    val phone: String,
    val email: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
