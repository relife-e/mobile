package com.example.propertywatch.database


import android.content.Context
import java.util.concurrent.Executors

class PropertyRepository private constructor(context: Context) {
    private val database: PropertyDatabase = PropertyDatabase.getInstance(context)

    private val propertyDao = database.propertyDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getProperties() = propertyDao.getProperties()

    fun addProperties(properties: List<PropertyWatchList>) {
        executor.execute {
            propertyDao.addProperties(properties)
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



            val properties = listOf((PropertyWatchList( address="149-153 Bunda Street, Cairns", price=200000, phone="0403404111", lat=-
            16.928893272553427, lon=145.77021565990813)),
            (PropertyWatchList( address="197 Draper Street, Cairns", price=350000, phone="0403404222", lat=-16.928893272553427,
                lon=145.77021565990813)),
            (PropertyWatchList(
                address="198 Grafton Street, Cairns", price=800000, phone="0403404333",
                        lat=-16.916479904985984, lon=145.76987256094102)),

            (PropertyWatchList( address="3 Cominos Place, Cairns", price=550000,
                    phone="0403404444", lat=-16.922791,
                lon=145.745504)),
            (PropertyWatchList( address="6 McGuigan Street, Earlville", price=400000,
                    phone="0403404555", lat=-16.945571,
                lon=145.741207)),
            (PropertyWatchList(
                address="6-8 Quigley Street, Manunda",
                price=455000, phone="0403404666", lat=-
                16.929026, lon=145.762279)))


            INSTANCE?.addProperties(properties)
            testDataLoaded = true
        }
    }
}
