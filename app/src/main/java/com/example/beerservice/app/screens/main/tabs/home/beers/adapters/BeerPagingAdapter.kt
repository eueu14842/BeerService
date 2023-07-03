package com.example.beerservice.app.screens.main.tabs.home.beers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.databinding.ItemBeerCardBinding

interface OnBeerClickListener {
    fun onBeerClick(beer: Beer, position: Int)
}

class BeerPagingAdapter(
    private val beerListener: BeerListener
) : PagingDataAdapter<Beer, BeerPagingAdapter.Holder>(BeerDiffCallback()), View.OnClickListener {


    override fun onClick(v: View?) {
        val beer = v?.tag as Beer
        when (v.id) {
            R.id.rating_beer_stars -> {

            }
            R.id.textViewBeerFeedBack -> {

            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val beer: Beer = getItem(position) ?: return
        with(holder.binding) {
            Glide.with(holder.itemView).load(beer.image).into(imageViewBeer)
            textViewBeerTitle.text = beer.name
            textViewBeerDesc.text = beer.description
            ratingBeerStars
            textViewBeerFeedBack.setOnClickListener {
                beerListener.onNavigateToCreateFeedback(beer.id!!)
            }

        }

        holder.itemView.setOnClickListener {
            beerListener.onNavigateToBeerDetails(beer.id!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerCardBinding.inflate(inflater)
        return Holder(binding)
    }


    class Holder(val binding: ItemBeerCardBinding) : RecyclerView.ViewHolder(binding.root)

    interface BeerListener {
        fun onNavigateToBeerDetails(beerId: Int)
        fun onToggleRatingBar()
        fun onNavigateToCreateFeedback(beerId: Int)
    }


}

class BeerDiffCallback : DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem == newItem
    }

}