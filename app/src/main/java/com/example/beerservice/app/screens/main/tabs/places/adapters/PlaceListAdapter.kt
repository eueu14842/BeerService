package com.example.beerservice.app.screens.main.tabs.places.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.databinding.ItemPlaceBinding

class PlaceListAdapter(
    val list: List<Place>,
    val favoriteListener: PlacePagingAdapter.Listener
) :
    RecyclerView.Adapter<PlaceListAdapter.PlaceHolder>(), View.OnClickListener {

    override fun onClick(v: View?) {
        val place = v?.tag as Place
        when (v.id) {
            R.id.heartImageView -> {
                favoriteListener.onToggleFavoriteFlag(
                    place.placeId!!, false
                )
            }
            R.id.textViewShowPlaceOnMap -> favoriteListener.onNavigateToMap()
            else -> {
                favoriteListener.onNavigateToPlaceDetails(place.placeId!!)
            }
        }
    }

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
            loadPhoto(imageViewPlace, place.image)

            textViewPlaceTitle.text = place.name
            textViewPlaceDesc.text = place.description
            textViewPlaceCity.text = place.city
            textViewPlaceAddress.text = place.address

//            holder.binding.heartImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            updateFavoriteIcon(holder, place.setAvailabilityOfSpaceForTheUser)
            heartImageView.setOnClickListener {
                favoriteListener.onToggleFavoriteFlag(place.placeId!!, false)
            }
            holder.itemView.setOnClickListener {
                favoriteListener.onNavigateToPlaceDetails(place.placeId!!)
            }
        }
    }

    private fun updateFavoriteIcon(holder: PlaceHolder, isFavorite: Boolean?) {
        if (isFavorite == null) holder.binding.heartImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
        else {
            val iconResId = if (isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            holder.binding.heartImageView.setImageResource(iconResId)
        }
    }

/*    interface FavoriteListener {

        fun onNavigateToPlaceDetails(placeId: Int)

        fun onNavigateToMap()

        fun onToggleFavoriteFlag(placeId: Int)

    }*/

    override fun getItemCount() = list.size

    private fun loadPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }


}