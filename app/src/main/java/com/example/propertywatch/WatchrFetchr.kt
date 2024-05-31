package com.example.propertywatch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertywatch.photogallery.WatchrResponse
import com.example.propertywatch.photogallery.PropertyItem
import com.example.propertywatch.photogallery.PropertyResponse
import com.example.propertywatch.photogallery.WatchrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "WatchrFetchr"

class WatchrFetchr {

    private val watchrApi: WatchrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://jellymud.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        watchrApi = retrofit.create(WatchrApi::class.java)
    }

    fun fetchProperties(): LiveData<List<PropertyItem>> {
        val responseLiveData: MutableLiveData<List<PropertyItem>> = MutableLiveData()
        val request: Call<WatchrResponse> = watchrApi.fetchProperties()

        request.enqueue(object : Callback<WatchrResponse> {

            override fun onFailure(call: Call<WatchrResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch properties", t)
            }

            override fun onResponse(call: Call<WatchrResponse>, response: Response<WatchrResponse>) {
                Log.d(TAG, "Response received")

                val watchrResponse: WatchrResponse? = response.body()
                val propertyResponse: PropertyResponse? = watchrResponse?.properties
                var propertyItems: List<PropertyItem> = propertyResponse?.propertyItems ?: mutableListOf()


                responseLiveData.value = propertyItems
            }
        })

        return responseLiveData
    }
}
