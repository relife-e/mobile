package com.example.propertywatch.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.Update
import java.util.concurrent.Executors
@Dao
interface PropertyDao {
    @Query("SELECT * FROM property")
    fun getProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id = :id")
    fun getPropertyById(id: String): LiveData<Property?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProperties(properties: List<Property>)
    @Update
    fun updateProperty(property: Property)

    @Insert
    fun addProperty(properties: Property)

    @Insert
    fun addProperties(properties: List<Property>)


}

class PropertyRepository private constructor(context: Context) {
    private val database: PropertyDatabase = Room.databaseBuilder(
        context.applicationContext, PropertyDatabase::class.java, "properties_database"
    ).fallbackToDestructiveMigration()
        .build()
    private val propertyDao = database.propertyDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getProperties() : LiveData<List<Property>> = propertyDao.getProperties()
    fun saveProperties(properties: List<Property>) {
        executor.execute {
            propertyDao.saveProperties(properties)
        }
    }
    fun addProperties(properties: List<Property>) {
        executor.execute {
            propertyDao.addProperties(properties)
        }
    }


    companion object {

        private var INSTANCE: PropertyRepository? = null
        private var testDataLoaded = false

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PropertyRepository(context)
            }
        }

        fun get(): PropertyRepository {
            return INSTANCE ?: throw IllegalStateException("PropertyRepository not initialized")
        }

       fun loadTestData() {
            if (testDataLoaded)
                return

            val properties = listOf(
                Property(
                    address = "149-153 Bunda Street, Cairns",
                    price = 200000,
                    phone = "0403404111",
                    lat = -16.928893272553427,
                    lon = 145.77021565990813
                ),
                Property(
                    address = "197 Draper Street, Cairns",
                    price = 350000,
                    phone = "0403404222",
                    lat = -16.928893272553427,
                    lon = 145.77021565990813
                ),
                Property(
                    address = "198 Grafton Street, Cairns",
                    price = 800000,
                    phone = "0403404333",
                    lat = -16.916479904985984,
                    lon = 145.76987256094102
                ),
                Property(
                    address = "3 Cominos Place, Cairns",
                    price = 550000,
                    phone = "0403404444",
                    lat = -16.922791,
                    lon = 145.745504
                ),
                Property(
                    address = "6 McGuigan Street, Earlville",
                    price = 400000,
                    phone = "0403404555",
                    lat = -16.945571,
                    lon = 145.741207
                ),
                Property(
                    address = "6-8 Quigley Street, Manunda",
                    price = 455000,
                    phone = "0403404666",
                    lat = -16.929026,
                    lon = 145.762279
                )
            )

            INSTANCE?.addProperties(properties)
            testDataLoaded = true
        }
    }
}
