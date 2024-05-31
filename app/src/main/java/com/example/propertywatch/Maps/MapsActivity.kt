package com.example.propertywatch.Maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertywatch.R
import com.example.propertywatch.database.Property

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.propertywatch.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    var mName: String? = null
    var mLatitude = 0.0
    var mLongitude = 0.0
    var price = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (intent != null && intent.hasExtra("map_data"))
        {
            val mapData = intent.getSerializableExtra("map_data") as Property


            mLatitude = mapData.lat
            mLongitude = mapData.lon
            price = mapData.price
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap

        val latLon = LatLng(mLatitude, mLongitude)

        mMap.addMarker(MarkerOptions().position(latLon).title(price.toString()))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLon, 14f))
    }
}

