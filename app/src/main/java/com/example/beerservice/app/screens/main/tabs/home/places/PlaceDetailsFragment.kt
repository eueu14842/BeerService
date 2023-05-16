package com.example.beerservice.app.screens.main.tabs.home.places

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.Empty
import com.example.beerservice.app.model.ErrorResult
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerClickListener
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlaceDetailsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class PlaceDetailsFragment : BaseFragment(R.layout.fragment_place_details) {
    lateinit var binding: FragmentPlaceDetailsBinding
    override val viewModel: PlaceDetailsViewModel by viewModels { ViewModelFactory() }
    private val args by navArgs<PlaceDetailsFragmentArgs>()
    lateinit var recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceDetailsBinding.bind(view)

        setupBeersList()
        setupBreweryDetailsBlock()

    }


    private fun setupBeersList() {
        val adapter = BeerPagingAdapter(onBeerClickListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerBeerByPlaceDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        observeLoadState(adapter)
        observeBeers(adapter)


    }
    private fun setupBreweryDetailsBlock() {
        viewModel.getPlace(args.placeId)
        viewModel.place.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    with(binding) {
                        Glide.with(this@PlaceDetailsFragment)
                            .load(it.value.image)
                            .into(imageViewPlace)
                        textViewPlaceDesc.text = it.value.description
                        textViewPlaceTitle.text = it.value.name
                        textViewPlaceAddress.text = it.value.city
                        textViewPlaceCity.text = it.value.city
                        setupPlaceId(it.value.placeId!!)
                    }
                }
                is Empty -> TODO()
            }
        }
    }
    private fun setupPlaceId(id: Int) {
        viewModel.setPlaceId(id)
    }


    private fun observeBeers(adapter: BeerPagingAdapter) {
        viewModel.getBeersByPlaceId()
        lifecycleScope.launch {
            viewModel.beersFlow?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: BeerPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
//                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }
    private val onBeerClickListener = object : OnBeerClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction =
                PlaceDetailsFragmentDirections.actionPlaceDetailsFragmentToBeerDetailsFragment2(beer.id!!)
            findNavController().navigate(direction)
        }
    }

}