package com.example.propertywatch.Maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.propertywatch.R
import com.example.propertywatch.database.PropertyWatchList

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.propertywatch.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    var mName: String? = null
    var mLatitude = 0.0
    var mLongitude = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (intent != null && intent.hasExtra("map_data"))
        {
            val mapData = intent.getSerializableExtra("map_data") as PropertyWatchList


            mLatitude = mapData.lat
            mLongitude = mapData.lon

        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap

        val latLon = LatLng(mLatitude, mLongitude)

        mMap.addMarker(MarkerOptions().position(latLon).title("mMessage"))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLon, 14f))
    }
}

