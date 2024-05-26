package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertywatch.photogallery.WatchrResponse
import com.example.propertywatch.photogallery.PropertyItem
import com.example.propertywatch.photogallery.PropertitesResponse
import com.example.propertywatch.photogallery.WatchrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class WatchrFetchr {

    private val watchrApi: WatchrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        watchrApi = retrofit.create(WatchrApi ::class.java)
    }

    fun fetchProperties(): LiveData<List<PropertyItem>> {
        val responseLiveData: MutableLiveData<List<PropertyItem>> = MutableLiveData()
        val flickrRequest: Call<WatchrResponse> = watchrApi.fetchProperties()

        flickrRequest.enqueue(object : Callback<WatchrResponse> {

            override fun onFailure(call: Call<WatchrResponse>, t: Throwable) {
                Log.e(com.example.propertywatch.TAG, "Failed to fetch property", t)
            }

            override fun onResponse(
                call: Call<WatchrResponse>,
                response: Response<WatchrResponse>
            ) {
                Log.d(com.example.propertywatch.TAG, "Response received")
                val watchrResponse: WatchrResponse? = response.body()
                val propertyResponse: PropertitesResponse? = watchrResponse?.properties
                var propertyItems: List<PropertyItem> = propertyResponse?.propertyItem
                    ?: mutableListOf()
                propertyItems = propertyItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = propertyItems
            }
        })

        return responseLiveData
    }
}