package com.example.beerservice.app.screens.main.tabs.home.brewery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.databinding.ItemAdblockBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

interface OnBreweryClickListener {
    fun onBreweryClick(brewery: Brewery, position: Int)
}

class BreweryAdblockAdapter(
    private val breweryList: List<Brewery>,
    private val onBreweryClickListener: OnBreweryClickListener

) :
    RecyclerView.Adapter<BreweryAdblockAdapter.BreweryViewHolder>() {
    class BreweryViewHolder(val binding: ItemAdblockBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdblockBinding.inflate(inflater)

        return BreweryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        val brewery = breweryList[position]
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(brewery.image)
                .centerCrop()
                .transform(
                    RoundedCornersTransformation(
                        100,
                        0,
                        RoundedCornersTransformation.CornerType.TOP
                    )
                )
                .into(ivItemImage)
            tvItemName.text = brewery.name
        }
        holder.itemView.setOnClickListener {
            onBreweryClickListener.onBreweryClick(brewery, position)
        }
    }

    override fun getItemCount() = breweryList.size

}