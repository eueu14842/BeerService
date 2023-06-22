package com.example.beerservice.app.screens.main.tabs.places.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemPlaceBinding
import com.example.beerservice.databinding.ItemPlaceCardBinding


class PlacePagingAdapter(
    private val listener: Listener
) :
    PagingDataAdapter<Place, PlacePagingAdapter.Holder>(PlaceDiffCallback()), View.OnClickListener {


    override fun onClick(v: View?) {
        val place = v?.tag as Place
        when (v.id) {
            R.id.heartImageView -> listener.onToggleFavoriteFlag(place)
            R.id.textViewShowPlaceOnMap -> listener.onNavigateToMap()
            v.id -> listener.onNavigateToPlaceDetails()
        }

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val place: Place = getItem(position) ?: return
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(place.image)
                .into(imageViewPlace)
            textViewPlaceTitle.text = place.name
            textViewPlaceDesc.text = place.description
            textViewPlaceCity.text = place.city

            heartImageView.tag = place
            textViewShowPlaceOnMap.tag = place
            holder.itemView.tag = place
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceCardBinding.inflate(inflater)
        return Holder(binding)
    }

    class Holder(val binding: ItemPlaceCardBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {

        fun onNavigateToPlaceDetails()

        fun onNavigateToMap()

        fun onToggleFavoriteFlag(place: Place)
    }


}

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.placeId == newItem.placeId
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

}