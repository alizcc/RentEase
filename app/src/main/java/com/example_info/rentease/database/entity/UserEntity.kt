package com.example_info.rentease.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(
    val username: String,
    val password: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val image: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
