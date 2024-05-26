package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.propertywatch.photogallery.PropertyItem


class PropertyWatchViewModel : ViewModel() {

    val propertyItemLiveData: LiveData<List<PropertyItem>>
    lateinit var  watchrFetchr: WatchrFetchr
    init {
        propertyItemLiveData = watchrFetchr.fetchProperties()
    }
}
