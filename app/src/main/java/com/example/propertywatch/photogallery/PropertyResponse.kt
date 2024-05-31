package com.example.propertywatch.photogallery

import com.google.gson.annotations.SerializedName

class PropertyResponse {
   @SerializedName("property")
    lateinit var propertyItems: List<PropertyItem>
}
