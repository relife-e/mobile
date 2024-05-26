package com.example.propertywatch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.propertywatch.Maps.MapsActivity
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
            val emailButton: Button = view.findViewById(R.id.button)
            emailButton.setOnClickListener {
                sendEmail(property)
            }
        }

        fun bind(property: PropertyWatchList) {
            this.property = property
            val addressView: TextView = view.findViewById(R.id.address)
            val priceView: TextView = view.findViewById(R.id.price)
            val phoneNumView: TextView = view.findViewById(R.id.phoneNum)

            addressView.text = property.address
            priceView.text = "$" + property.price.toString()
            phoneNumView.text = property.phone
        }

        override fun onClick(v: View) {
            val intent = Intent(v.context, MapsActivity::class.java).apply {
                putExtra("map_data", property)
            }
            v.context.startActivity(intent)
        }

        private fun sendEmail(property: PropertyWatchList) {
            val emailSubject = view.context.resources.getString(R.string.email_subject)
            val emailBody = "Address: ${property.address}\nPrice: ${property.price}\nPhone: ${property.phone}\nLocation: ${property.lat}, ${property.lon}"

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }
            view.context.startActivity(Intent.createChooser(intent, "Send using: "))
        }
    }
}
