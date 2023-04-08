package com.example.beerservice.app.screens.main.tabs.home.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemBreweryBinding

class PlaceAdapter(val breweryList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.BreweryViewHolder>() {
    class BreweryViewHolder(val binding: ItemBreweryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreweryBinding.inflate(inflater)
        return BreweryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        val place = breweryList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(place.image)
                .into(ivBreweryImage)
            tvBreweryName.text = place.name
            tvBreweryDescription.text = place.description

        }
    }

    override fun getItemCount() = breweryList.size
}