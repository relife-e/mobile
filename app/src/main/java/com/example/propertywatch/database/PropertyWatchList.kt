package com.example.propertywatch.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity

data class PropertyWatchList (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val address: String,
    val price: Int,
    val phone: String,
)