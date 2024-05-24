package com.example.propertywatch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PropertyDao {


    @Query("SELECT * FROM propertyWatchList")
    fun getPropertites(): LiveData<List<PropertyWatchList>>

    @Query("SELECT * FROM PropertyWatchList WHERE id=(:id)")
    fun getPropertyId(id: String): LiveData<PropertyWatchList?>

    @Update
    fun updateProperty(propertyWatchList: PropertyWatchList)

    @Insert
    fun addProperty(propertyWatchList: PropertyWatchList)

    @Insert
    fun addPropertities(propertyWatchList: List<PropertyWatchList>)
}