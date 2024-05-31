package com.example.propertywatch.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PropertyWatchListViewModel : ViewModel() {
    private val propertyRepository = PropertyRepository.get()
    val propertyList = propertyRepository.getProperties()
}
