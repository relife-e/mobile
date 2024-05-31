package com.example.propertywatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.propertywatch.database.PropertyRepository
import com.example.propertywatch.database.Property
import com.example.propertywatch.database.PropertyWatchListViewModel

class PropertyWatchListFragment : Fragment() {

    private lateinit var mPropertyListViewModel: PropertyWatchListViewModel
    private lateinit var propertyWatchViewModel: PropertyWatchViewModel
    private val properties = mutableListOf<Property>()

    companion object {
        fun newInstance() = PropertyWatchListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPropertyListViewModel = ViewModelProvider(this).get(PropertyWatchListViewModel::class.java)
        propertyWatchViewModel = ViewModelProvider(this).get(PropertyWatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.property_watchlist_menu, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.property_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        mPropertyListViewModel.propertyList.observe(viewLifecycleOwner, Observer { propertyList ->
            if (propertyList.isEmpty()) {
                PropertyRepository.loadTestData()
            } else {
                recyclerView.adapter = PropertyWatchListAdapter(propertyList)
            }
        })

        propertyWatchViewModel.propertyItemLiveData.observe(viewLifecycleOwner, Observer { propertyItems ->
            val newProperties = propertyItems.map { propertyItem ->
                Property(
                    id = propertyItem.id,
                    address = propertyItem.address,
                    price = propertyItem.price,
                    phone = propertyItem.phone,
                    lat = propertyItem.lat,
                    lon = propertyItem.lon
                )
            }


            //recyclerView.adapter = PropertyWatchListAdapter(newProperties)
            PropertyRepository.get().saveProperties(newProperties)

        })

        return view
    }
}
