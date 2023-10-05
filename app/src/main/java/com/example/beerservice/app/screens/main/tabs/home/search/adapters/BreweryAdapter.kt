package com.example.beerservice.app.screens.main.tabs.home.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.OnBreweryClickListener
import com.example.beerservice.databinding.ItemBreweryCardBinding

class BreweryAdapter(
    val breweryList: List<Brewery>,
    private val onBreweryClickListener: OnBreweryClickListener
) :

    RecyclerView.Adapter<BreweryAdapter.BreweryViewHolder>() {

    class BreweryViewHolder(val binding: ItemBreweryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreweryCardBinding.inflate(inflater)
        return BreweryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        val brewery = breweryList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(brewery.image)
                .into(ivBreweryImage)
            tvBreweryName.text = brewery.name
            tvBreweryDescription.text = brewery.description
        }
        holder.itemView.setOnClickListener {
            onBreweryClickListener.onBreweryClick(brewery, position)
        }
    }

    override fun getItemCount() = breweryList.size
}