package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.propertywatch.database.PropertyWatchList

class PropertyViewModel : ViewModel() {
    val selectedProperty = MutableLiveData<PropertyWatchList>()
}