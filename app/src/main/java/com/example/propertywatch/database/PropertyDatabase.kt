package com.example.propertywatch.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Property::class], version = 9 , exportSchema = false)
abstract class PropertyDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao

}
