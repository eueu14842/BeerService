package com.example.beerservice.app.screens.main.tabs.home.beers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.databinding.ItemBeerBinding
import com.example.beerservice.databinding.ItemFeedbackBinding

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
            Glide.with(holder.itemView)
                .load(feedback.imageFeedback)
                .into(imageViewFeedbackAddedImage)
            textViewFeedbackUsername.text = feedback.userName
            textViewFeedbackText.text = feedback.feedbackText

        }
        holder.itemView.setOnClickListener {
            onFeedbackListener.onFeedbackClick(feedback, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedbackBinding.inflate(inflater)
        return Holder(binding)
    }

    class Holder(val binding: ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root)
}

class FeedbackDiffCallback : DiffUtil.ItemCallback<FeedbackBeer>() {
    override fun areItemsTheSame(oldItem: FeedbackBeer, newItem: FeedbackBeer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedbackBeer, newItem: FeedbackBeer): Boolean {
        return oldItem == newItem
    }

}