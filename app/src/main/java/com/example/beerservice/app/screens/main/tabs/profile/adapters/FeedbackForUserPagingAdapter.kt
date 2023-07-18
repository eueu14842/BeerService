package com.example.beerservice.app.screens.main.tabs.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.databinding.ItemFeedbackCardBinding
import com.example.beerservice.databinding.ItemProfileCardFeedbackBinding


class FeedbackForUserPagingAdapter(
) :
    PagingDataAdapter<FeedbackBeer, FeedbackForUserPagingAdapter.Holder>(FeedbackDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val feedback: FeedbackBeer = getItem(position) ?: return
        with(holder.binding) {
            textViewBreweryTitle.text = feedback.breweryName
            beerName.text = feedback.beerName
            loadPhoto(imageViewBeer, feedback.imageBeer)
            if (feedback.rating != null) ratingBeerStars.rating = feedback.rating
        }

    }

    private fun <T> loadPhoto(imageView: ImageView, image: T?) {
        val context = imageView.context
        Glide.with(context).load(image).into(imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileCardFeedbackBinding.inflate(inflater)
        return Holder(binding)
    }

    class Holder(val binding: ItemProfileCardFeedbackBinding) :
        RecyclerView.ViewHolder(binding.root)
}

class FeedbackDiffCallback : DiffUtil.ItemCallback<FeedbackBeer>() {
    override fun areItemsTheSame(oldItem: FeedbackBeer, newItem: FeedbackBeer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedbackBeer, newItem: FeedbackBeer): Boolean {
        return oldItem == newItem
    }

}