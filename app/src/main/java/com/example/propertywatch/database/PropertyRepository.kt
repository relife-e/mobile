package com.example.propertywatch.database

import PropertyDatabase
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class PropertyRepository private constructor(context: Context) {

    private val database : PropertyDatabase = Room.databaseBuilder(
        context.applicationContext, PropertyDatabase::class.java, "property-database").build()

    private val propertyDao = database.propertyDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getProperties(): LiveData<List<PropertyWatchList>> = propertyDao.getPropertites()

    fun addProperties(properties: List<PropertyWatchList>) {
        executor.execute {
            propertyDao.addPropertities(properties)
        }
    }

    companion object {
        private var INSTANCE: PropertyRepository? = null
        var testDataLoaded = false

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PropertyRepository(context)
            }
        }

        fun get(): PropertyRepository {
            return INSTANCE ?: throw IllegalStateException("PropertyRepository not initialized")
        }

        fun loadTestData() {
            if (testDataLoaded) return

            val properties = arrayListOf(
                PropertyWatchList(address = "123 Main Street, Springfield, 2323", price = 890000, phone = "890000"),
                PropertyWatchList(address = "456 Elm Avenue, Lakeside, 3023", price = 890000, phone = "720000"),
                PropertyWatchList(address = "789 Oak Lane, Mountainview, 4065", price = 890000, phone = "1550000"),
                PropertyWatchList(address = "321 Maple Drive, Rivertown, 1237", price = 890000, phone = "950000"),
                PropertyWatchList(address = "987 Pine Street, Sunset City, 5442", price = 890000, phone = "1012424")
            )

            INSTANCE?.addProperties(properties)
            testDataLoaded = true
        }
    }
}
