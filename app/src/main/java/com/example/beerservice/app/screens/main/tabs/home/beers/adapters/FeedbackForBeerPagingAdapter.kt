package com.example.beerservice.app.screens.main.tabs.home.beers.adapters

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

interface OnFeedbackClickListener {
    fun onFeedbackClick(feedbackBeer: FeedbackBeer, position: Int)
}

class FeedbackForBeerPagingAdapter(
    private val onFeedbackListener: OnFeedbackClickListener
) :
    PagingDataAdapter<FeedbackBeer, FeedbackForBeerPagingAdapter.Holder>(FeedbackDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val feedback: FeedbackBeer = getItem(position) ?: return
        with(holder.binding) {
            textViewFeedbackUsername.text = feedback.userName
            textViewFeedbackText.text = feedback.feedbackText
            textViewFeedbackDate.text = feedback.dateFeedback
            loadPhoto(imageViewFeedbackUserImg, R.drawable.ic_no_image)
            if (feedback.imageFeedback != null) {
                cardViewImage.visibility = View.VISIBLE
                loadPhoto(imageViewFeedbackAddedImage, feedback.imageFeedback)
            } else {
                cardViewImage.visibility = View.GONE
            }
            if (feedback.rating != null) ratingFeedbackStars.rating = feedback.rating

        }
        holder.itemView.setOnClickListener {
            onFeedbackListener.onFeedbackClick(feedback, position)
        }
    }

    private fun <T> loadPhoto(imageView: ImageView, image: T?) {
        val context = imageView.context
        Glide.with(context).load(image).into(imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedbackCardBinding.inflate(inflater)
        return Holder(binding)
    }

    class Holder(val binding: ItemFeedbackCardBinding) : RecyclerView.ViewHolder(binding.root)
}

class FeedbackDiffCallback : DiffUtil.ItemCallback<FeedbackBeer>() {
    override fun areItemsTheSame(oldItem: FeedbackBeer, newItem: FeedbackBeer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedbackBeer, newItem: FeedbackBeer): Boolean {
        return oldItem == newItem
    }

}