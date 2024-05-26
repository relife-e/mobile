package com.example.propertywatch.photogallery

import com.google.gson.annotations.SerializedName

class PropertitesResponse {
    @SerializedName("property")
    lateinit var propertyItem: List<PropertyItem>
}
