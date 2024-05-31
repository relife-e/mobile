package com.example.propertywatch


import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.propertywatch.database.PropertyRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


private const val TAG = "LocationServices"
private const val LOCATION_PERMISSION_CODE = 1

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PropertyRepository.initialize(this)

        if (savedInstanceState == null) {
            loadFragment(PropertyWatchListFragment.newInstance())
        }

        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlertDialog()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                // Handle location updates
            }
        }
    }

    private fun showAlertDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
            .setMessage("Please enable GPS.")
            .setPositiveButton("OK", null)
        alert.show()
    }

    private fun loadFragment(fragment: Fragment) {
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
