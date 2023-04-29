package com.example.beerservice.app.screens.main.tabs.home.beers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.databinding.ItemBreweryBinding

interface OnBeerAdblockClickListener {
    fun onBeerClick(beer: Beer, position: Int)
}

class BeerAdblockAdapter(
    val beerList: List<Beer>,
    val onBeerAdblockClickListener: OnBeerAdblockClickListener
) :
    RecyclerView.Adapter<BeerAdblockAdapter.BeerViewHolder>() {
    class BeerViewHolder(val binding: ItemBreweryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreweryBinding.inflate(inflater)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = beerList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(beer.image)
                .into(ivBreweryImage)
            tvBreweryName.text = beer.name
            tvBreweryDescription.text = beer.description
        }
        holder.itemView.setOnClickListener {
            onBeerAdblockClickListener.onBeerClick(beer,position)
        }
    }

    override fun getItemCount() = beerList.size
}