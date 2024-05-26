package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.propertywatch.database.PropertyRepository
import com.example.propertywatch.database.PropertyWatchList
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import androidx.appcompat.app.AlertDialog
import com.example.propertywatch.R

const val TAG = "LocationServices"
private const val LOCATION_PERMISSION_CODE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var mPropertyViewModel: PropertyViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var locationCallback: LocationCallback? = null // Initialize as null to check later
    private lateinit var property: PropertyWatchList
    var mLatitude = 0.0
    var mLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PropertyRepository.initialize(this)
        PropertyRepository.loadTestData()

        mPropertyViewModel = ViewModelProvider(this).get(PropertyViewModel::class.java)

        mPropertyViewModel.selectedProperty.observe(this) {
            loadFragment(PropertyWatchListFragment.newInstance())
        }

        if (savedInstanceState == null) {
            loadFragment(PropertyWatchListFragment.newInstance())
        }

        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlertDialog()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize locationCallback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                // Handle location updates
            }
        }

        // Request location updates or permissions here as needed
    }

    private fun showAlertDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
            .setMessage("Please fill in all fields correctly.")
            .setPositiveButton("OK", null)
        alert.show()
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != LOCATION_PERMISSION_CODE) return

        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "Permission denied")
            // Provide feedback to the user regarding the need for the permission
            finish()
        } else {
            Log.d(TAG, "Permission granted")
        }
    }

    private fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}
