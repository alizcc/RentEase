package com.example_info.rentease.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example_info.rentease.database.entity.UserEntity

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM user_entity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM user_entity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserEntity>

    @Query("SELECT * FROM user_entity WHERE id=:userId LIMIT 1")
    fun findById(userId: Long): UserEntity?

    @Query("SELECT * FROM user_entity WHERE username=:username LIMIT 1")
    fun findByUsername(username: String): UserEntity?

    @Query("SELECT * FROM user_entity WHERE email=:email LIMIT 1")
    fun findByEmail(email: String): UserEntity?

    @Update
    fun updateUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}
