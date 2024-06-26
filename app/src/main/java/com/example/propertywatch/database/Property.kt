package com.example.propertywatch.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity
data class Property(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val address: String,
    val price: Int,
    val phone: String,
    val lat: Double,
    val lon: Double
): Serializable
