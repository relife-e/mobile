package com.example.propertywatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.propertywatch.database.PropertyWatchList

class PropertyWatchListAdapter(private val properties: List<PropertyWatchList>) : RecyclerView.Adapter<PropertyWatchListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_watch_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = properties.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]
        holder.bind(property)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var property: PropertyWatchList

        init {
            view.setOnClickListener(this)
        }

        fun bind(property: PropertyWatchList) {
            this.property = property
            val addressView: TextView = view.findViewById(R.id.address)
            val priceView: TextView = view.findViewById(R.id.price)
            val phN: TextView = view.findViewById(R.id.phoneNum)

            addressView.text = property.address
            priceView.text = "$" + property.price.toString()
            phN.text = property.phone.toString()
        }

        override fun onClick(v: View?) {
            // Handle the click event if needed
        }
    }
}
