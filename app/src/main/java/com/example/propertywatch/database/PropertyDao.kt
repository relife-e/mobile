package com.example.propertywatch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PropertyDao {
    @Query("SELECT * FROM PropertyWatchList")
    fun getProperties(): LiveData<List<PropertyWatchList>>

    @Query("SELECT * FROM PropertyWatchList WHERE id = :id")
    fun getPropertyById(id: String): LiveData<PropertyWatchList?>

    @Update
    fun updateProperty(propertyWatchList: PropertyWatchList)

    @Insert
    fun addProperty(propertyWatchList: PropertyWatchList)

    @Insert
    fun addProperties(propertyWatchList: List<PropertyWatchList>)
}
