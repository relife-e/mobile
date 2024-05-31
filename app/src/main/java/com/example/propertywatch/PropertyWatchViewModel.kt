package com.example.propertywatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.propertywatch.photogallery.PropertyItem

class PropertyWatchViewModel : ViewModel() {
    val propertyItemLiveData: LiveData<List<PropertyItem>>

    init {
        val watchrFetchr = WatchrFetchr()
        propertyItemLiveData = watchrFetchr.fetchProperties()
    }
}
