package com.example.beerservice.app.screens.main.tabs.home.brewery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.main.tabs.home.brewery.OnBreweryClickListener
import com.example.beerservice.databinding.ItemBreweryBinding


interface OnBreweryClickListener {
    fun onBreweryClick(brewery: Brewery, position: Int)
}
class BreweryPagingAdapter (
    private val onBreweryClickListener: OnBreweryClickListener
        ):
    PagingDataAdapter<Brewery, BreweryPagingAdapter.Holder>(BreweryDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val brewery = getItem(position) ?: return
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(brewery.image)
                .into(ivBreweryImage)
            tvBreweryName.text = brewery.name
            tvBreweryDescription.text = brewery.description
        }
        holder.itemView.setOnClickListener {
            onBreweryClickListener.onBreweryClick(brewery, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreweryBinding.inflate(inflater)
        return Holder(binding)
    }


    class Holder(val binding: ItemBreweryBinding) : RecyclerView.ViewHolder(binding.root)
}

class BreweryDiffCallback : DiffUtil.ItemCallback<Brewery>() {
    override fun areItemsTheSame(oldItem: Brewery, newItem: Brewery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Brewery, newItem: Brewery): Boolean {
        return oldItem == newItem
    }

}