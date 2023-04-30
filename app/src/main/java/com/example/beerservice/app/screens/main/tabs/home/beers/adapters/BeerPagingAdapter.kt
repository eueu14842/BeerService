package com.example.beerservice.app.screens.main.tabs.home.beers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.databinding.ItemBeerBinding

interface OnBeerClickListener {
    fun onBeerClick(beer: Beer, position: Int)
}
class BeerPagingAdapter (
    private val onBeerClickListener: OnBeerClickListener
):
    PagingDataAdapter<Beer, BeerPagingAdapter.Holder>(BeerDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val beer: Beer = getItem(position) ?: return
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(beer.image)
                .into(imageViewBeer)
            textViewBeerTitle.text = beer.name
            textViewBeerDesc.text = beer.description
        }
        holder.itemView.setOnClickListener {
            onBeerClickListener.onBeerClick(beer, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater)
        return Holder(binding)
    }


    class Holder(val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root)
}

class BeerDiffCallback : DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem == newItem
    }

}