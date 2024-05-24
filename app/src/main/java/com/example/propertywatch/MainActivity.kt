package com.example.propertywatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.propertywatch.database.PropertyRepository
import com.example.propertywatch.database.PropertyWatchListViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var mPropertyViewModel: PropertyViewModel

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
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
