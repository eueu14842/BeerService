package com.example.beerservice.app.screens.main.tabs.places.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemAdblockBinding
import com.example.beerservice.databinding.ItemAdblockPlaceBinding
import com.example.beerservice.databinding.ItemBreweryBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
interface OnPlaceClickListener {
    fun onPlaceClick(place: Place, position: Int)
}
class PlaceAdblockAdapter(
    private val breweryList: List<Place>,
    val onPlaceAdblockClickListener: OnPlaceClickListener
) :
    RecyclerView.Adapter<PlaceAdblockAdapter.BreweryViewHolder>() {
    class BreweryViewHolder(val binding: ItemAdblockPlaceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdblockPlaceBinding.inflate(inflater)
        return BreweryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        val place = breweryList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(place.image)
                .into(ivPlaceImage)
            tvPlaceName.text = place.name

            imageViewIsFavorite.setImageResource(
                if (place.setAvailabilityOfSpaceForTheUser == true) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )

        }
        holder.itemView.setOnClickListener {
            onPlaceAdblockClickListener.onPlaceClick(place, position)
        }
    }

    override fun getItemCount() = breweryList.size
}