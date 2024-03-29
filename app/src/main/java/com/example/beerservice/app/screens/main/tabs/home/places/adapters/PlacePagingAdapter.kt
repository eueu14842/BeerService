package com.example.beerservice.app.screens.main.tabs.home.places.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemBeerBinding
import com.example.beerservice.databinding.ItemPlaceBinding

interface OnPlaceClickListener {
    fun onPlaceClick(place: Place, position: Int)
}

class PlacePagingAdapter(
    private val onPlaceClickListener: OnPlaceClickListener
) :
    PagingDataAdapter<Place, PlacePagingAdapter.Holder>(PlaceDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val place: Place = getItem(position) ?: return
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(place.image)
                .into(imageViewPlace)
            textViewPlaceTitle.text = place.name
            textViewPlaceDesc.text = place.description
            textViewPlaceAddress.text = place.address
            textViewPlaceCity.text = place.city
        }
        holder.itemView.setOnClickListener {
            onPlaceClickListener.onPlaceClick(place, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater)
        return Holder(binding)
    }


    class Holder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)
}

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.placeId == newItem.placeId
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

}