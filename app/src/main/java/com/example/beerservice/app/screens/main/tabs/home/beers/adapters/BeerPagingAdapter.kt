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
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.databinding.ItemBeerCardBinding

interface OnBeerClickListener {
    fun onBeerClick(beer: Beer, position: Int)
}

class BeerPagingAdapter(
    private val beerListener: BeerListListener
) : PagingDataAdapter<Beer, BeerPagingAdapter.Holder>(BeerDiffCallback()), View.OnClickListener {


    override fun onClick(v: View?) {
        val beer = v?.tag as Beer
        when (v.id) {
            R.id.textViewBeerFeedBack ->
                beerListener.onNavigateToCreateFeedback(beer.id!!)
            else -> beerListener.onNavigateToBeerDetails(beer.id!!)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val beer: Beer = getItem(position) ?: return

        with(holder.binding) {
            root.tag = beer
            textViewBeerFeedBack.tag = beer

            loadPhoto(imageViewBeer, beer.image)
            textViewBeerTitle.text = beer.name
            textViewBeerDesc.text = beer.description
            stileBeer.text = beer.style
            abv.text = beer.abv.toString()
            ibu.text = beer.ibu.toString()
            beerRating.text = beer.averageRating.toString()
            if (beer.averageRating != null) ratingBeerStars.rating = beer.averageRating


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerCardBinding.inflate(inflater)

        binding.textViewBeerFeedBack.setOnClickListener(this)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    private fun loadPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }

    class Holder(val binding: ItemBeerCardBinding) : RecyclerView.ViewHolder(binding.root)

    interface BeerListListener {
        fun onNavigateToBeerDetails(beerId: Int)
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