package com.example.propertywatch.photogallery


import retrofit2.Call
import retrofit2.http.GET

interface WatchrApi {
    @GET("properties.json")
    fun fetchProperties(): Call<WatchrResponse>
}
