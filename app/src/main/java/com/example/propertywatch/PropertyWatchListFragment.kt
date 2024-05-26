package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.propertywatch.R
import com.example.propertywatch.database.PropertyRepository
import com.example.propertywatch.database.PropertyWatchList
import com.example.propertywatch.database.PropertyWatchListViewModel

class PropertyWatchListFragment : Fragment() {
    private val properties = mutableListOf<PropertyWatchList>()
    private lateinit var propertyWatchListAdapter: PropertyWatchListAdapter
    private lateinit var propertyWatchListViewModel: PropertyWatchViewModel
    private lateinit var mPropertyViewModel: PropertyWatchListViewModel

    companion object {
        fun newInstance() = PropertyWatchListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = activity as ViewModelStoreOwner

        mPropertyViewModel = ViewModelProvider(context).get(PropertyWatchListViewModel::class.java)
        propertyWatchListViewModel = ViewModelProvider(context).get(PropertyWatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.property_watchlist_menu, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.property_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        mPropertyViewModel.propertyList.observe(viewLifecycleOwner, Observer { propertyList ->
            if (propertyList.isEmpty()) {
                PropertyRepository.loadTestData()  // triggers observer to run again as data will change
                return@Observer
            }
            recyclerView.adapter = PropertyWatchListAdapter(propertyList)
        })

        propertyWatchListViewModel.propertyItemLiveData.observe(viewLifecycleOwner, Observer { propertyItems ->
            val propertyList = propertyItems.map { propertyItem ->
                PropertyWatchList(
                    address = propertyItem.address,
                    price = propertyItem.price,
                    phone = propertyItem.phone,
                    lat = propertyItem.lat,
                    lon = propertyItem.lon
                )
            }
            properties.clear()
            properties.addAll(propertyList)
            PropertyRepository.get().addProperties(properties)
        })

        return view
    }
}
