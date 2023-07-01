package com.example.beerservice.app.screens.main.tabs.home.places.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemAdblockPlaceBinding


class PlaceAdblockAdapter(
    private val breweryList: List<Place>, val listener: Listener
) : RecyclerView.Adapter<PlaceAdblockAdapter.PlaceAdblockViewHolder>(), View.OnClickListener {

    override fun onClick(v: View?) {
        val place = v?.tag as Place
        when (v.id) {
            R.id.heartImageView -> {
                listener.onToggleFavoriteFlag(
                    place.placeId!!, place.setAvailabilityOfSpaceForTheUser!!
                )
            }
            else -> {
                listener.onNavigateToPlaceDetails(place.placeId!!)
            }
        }
    }

    class PlaceAdblockViewHolder(val binding: ItemAdblockPlaceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdblockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdblockPlaceBinding.inflate(inflater)
        binding.heartImageView.setOnClickListener(this)
        binding.root.setOnClickListener(this)
        return PlaceAdblockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceAdblockViewHolder, position: Int) {
        val place = breweryList[position]
        with(holder.binding) {
            loadPhoto(ivPlaceImage, place.image)
            tvPlaceName.text = place.name

            heartImageView.tag = place
            holder.itemView.tag = place

            heartImageView.setOnClickListener {
                listener.onToggleFavoriteFlag(
                    place.placeId!!, place.setAvailabilityOfSpaceForTheUser!!
                )
                val newIsFavorite = !place.setAvailabilityOfSpaceForTheUser!!
                place.setAvailabilityOfSpaceForTheUser = newIsFavorite
                notifyItemChanged(position)
                updateFavoriteIcon(holder, newIsFavorite)
            }
        }
        updateFavoriteIcon(holder, place.setAvailabilityOfSpaceForTheUser!!)
    }

    private fun updateFavoriteIcon(holder: PlaceAdblockViewHolder, isFavorite: Boolean) {
        val iconResId = if (isFavorite) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        holder.binding.heartImageView.setImageResource(iconResId)
    }

    override fun getItemCount() = breweryList.size

    interface Listener {

        fun onNavigateToPlaceDetails(placeId: Int)

        fun onToggleFavoriteFlag(placeId: Int, isFavorite: Boolean)

    }

    private fun loadPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }


}