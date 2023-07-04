package com.example.beerservice.app.screens.main.tabs.home.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.databinding.ItemBeerCardBinding

class BeersAdapter(
    private val listener: BeerSearchListListener,
    private val beerList: List<Beer>
) : RecyclerView.Adapter<BeersAdapter.BeerViewHolder>(), View.OnClickListener {

    override fun onClick(v: View?) {
        val beer = v?.tag as Beer
        when (v.id) {
            R.id.textViewBeerFeedBack -> {
                listener.onNavigateToCreateFeedback(beer.id!!)
                println("onClick ${beer.id}")
            }
            else -> listener.onNavigateToBeerDetails(beer.id!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerCardBinding.inflate(inflater)

        binding.textViewBeerFeedBack.setOnClickListener(this)
        binding.root.setOnClickListener(this)

        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = beerList[position]
        with(holder.binding) {
            loadPhoto(imageViewBeer, beer.image)
            textViewBeerTitle.text = beer.name
            textViewBeerDesc.text = beer.description
            abv.text = beer.abv.toString()
            ibu.text = beer.ibu.toString()

            holder.itemView.tag = beer
            textViewBeerFeedBack.tag = beer
        }
    }

    private fun loadPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }

    override fun getItemCount() = beerList.size

    class BeerViewHolder(val binding: ItemBeerCardBinding) : RecyclerView.ViewHolder(binding.root)

    interface BeerSearchListListener {
        fun onNavigateToBeerDetails(beerId: Int)
        fun onNavigateToCreateFeedback(beerId: Int)
    }


}