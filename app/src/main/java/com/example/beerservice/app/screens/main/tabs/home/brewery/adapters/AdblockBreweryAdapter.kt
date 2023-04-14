package com.example.beerservice.app.screens.main.tabs.home.brewery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.databinding.ItemBreweryBinding

class AdblockBreweryAdapter(
    val breweryList: List<Brewery>
) : RecyclerView.Adapter<AdblockBreweryAdapter.HomeViewHolder>() {
    class HomeViewHolder(val binding: ItemBreweryBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreweryBinding.inflate(inflater)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val brewery = breweryList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(brewery.image)
                .into(ivBreweryImage)
            tvBreweryName.text = brewery.name
            tvBreweryDescription.text = brewery.description

        }
    }

    override fun getItemCount(): Int = breweryList.size

}