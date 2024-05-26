package com.example.myapplication


import com.example.propertywatch.photogallery.WatchrResponse
import retrofit2.Call
import retrofit2.http.GET

interface WatchrApi {

    @GET("properties.json")
    fun fetchProperties(): Call<WatchrResponse>

    fun fetchPhotos(): Call<WatchrResponse>
}