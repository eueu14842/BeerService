package com.example.beerservice.app.screens.main.tabs.places.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemPlaceBinding

class PlaceListAdapter(
    val list: List<Place>,
) :
    RecyclerView.Adapter<PlaceListAdapter.PlaceHolder>() {
    class PlaceHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater)

        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val place = list[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(place.image)
                .into(imageViewPlace)
            textViewPlaceTitle.text = place.name
            textViewPlaceDesc.text = place.description
            textViewPlaceCity.text = place.city
            textViewPlaceAddress.text = place.address

            heartImageView.setImageResource(
                if (place.setAvailabilityOfSpaceForTheUser == true) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )
        }
        holder.binding.textViewPlaceMagazine.setOnClickListener {
            Toast.makeText(holder.itemView.context, "ASDAS a DAS ", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount() = list.size
}