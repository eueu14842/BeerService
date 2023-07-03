package com.example.beerservice.app.screens.main.tabs.home.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.feedback.entities.FeedbackBeerCreate
import com.example.beerservice.databinding.ItemBeerCardBinding

class BeersAdapter(val beerList: List<Beer>) :
    RecyclerView.Adapter<BeersAdapter.BeerViewHolder>() {
    class BeerViewHolder(val binding: ItemBeerCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerCardBinding.inflate(inflater)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = beerList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(beer.image)
                .into(imageViewBeer)
            textViewBeerTitle.text = beer.name
            textViewBeerDesc.text = beer.description
        }
    }


    override fun getItemCount() = beerList.size
}