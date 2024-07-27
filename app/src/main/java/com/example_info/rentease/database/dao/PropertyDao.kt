package com.example_info.rentease.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example_info.rentease.database.entity.PropertyEntity

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(propertyList: List<PropertyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(property: PropertyEntity)

    @Update
    fun update(property: PropertyEntity)

    @Query("DELETE FROM property_entity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM property_entity WHERE id = :id")
    fun getById(id: Long): PropertyEntity?

    @Query("SELECT * FROM property_entity")
    fun getAll(): List<PropertyEntity>

    @Query("SELECT * FROM property_entity WHERE creatorId = :userId")
    fun getAllByUserId(userId: String): List<PropertyEntity>

    @Query("SELECT * FROM property_entity WHERE type = :type")
    fun getAllByType(type: String): List<PropertyEntity>
}
