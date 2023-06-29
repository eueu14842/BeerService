package com.example.beerservice.app.screens.main.tabs.places.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemPlaceBinding

class PlaceListAdapter(
    val list: List<Place>,
    val listener: Listener
) :
    RecyclerView.Adapter<PlaceListAdapter.PlaceHolder>(), View.OnClickListener {

    class PlaceHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater)

        binding.heartImageView.setOnClickListener(this)
        binding.textViewShowPlaceOnMap.setOnClickListener(this)
        binding.root.setOnClickListener(this)

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

            heartImageView.setOnClickListener {
                listener.onToggleFavoriteFlag(
                    place.placeId!!, place.setAvailabilityOfSpaceForTheUser!!
                )
                val newIsFavorite = !place.setAvailabilityOfSpaceForTheUser!!
                place.setAvailabilityOfSpaceForTheUser = newIsFavorite
                notifyItemChanged(position)
                println(place.setAvailabilityOfSpaceForTheUser)
                updateFavoriteIcon(holder, newIsFavorite)
            }
        }
        holder.binding.textViewPlaceMagazine.setOnClickListener {
            Toast.makeText(holder.itemView.context, "ASDAS a DAS ", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
        }
    }

    private fun updateFavoriteIcon(holder: PlaceListAdapter.PlaceHolder, isFavorite: Boolean) {
        val iconResId = if (isFavorite) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        holder.binding.heartImageView.setImageResource(iconResId)
    }

    interface Listener {

        fun onNavigateToPlaceDetails(placeId: Int)

        fun onNavigateToMap()

        fun onToggleFavoriteFlag(placeId: Int, isFavorite: Boolean)

    }

    override fun getItemCount() = list.size

    override fun onClick(v: View?) {
        val place = v?.tag as Place
        when (v.id) {
            R.id.heartImageView -> {
                listener.onToggleFavoriteFlag(
                    place.placeId!!, place.setAvailabilityOfSpaceForTheUser!!
                )
            }
            R.id.textViewShowPlaceOnMap -> listener.onNavigateToMap()
            else -> {
                listener.onNavigateToPlaceDetails(place.placeId!!)
            }
        }
    }
}