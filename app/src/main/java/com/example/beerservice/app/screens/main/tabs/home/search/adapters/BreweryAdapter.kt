package com.example.beerservice.app.screens.main.tabs.home.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.databinding.ItemBreweryCardBinding

class BreweryAdapter(val beerList: List<Brewery>) :
    RecyclerView.Adapter<BreweryAdapter.BreweryViewHolder>() {


    class BreweryViewHolder(val binding: ItemBreweryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreweryCardBinding.inflate(inflater)
        return BreweryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        val beer = beerList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(beer.image)
                .into(ivBreweryImage)
            tvBreweryName.text = beer.name
            tvBreweryDescription.text = beer.description

        }
    }

    override fun getItemCount() = beerList.size
}