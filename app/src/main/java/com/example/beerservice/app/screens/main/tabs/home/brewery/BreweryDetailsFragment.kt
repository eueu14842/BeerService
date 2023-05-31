package com.example.beerservice.app.screens.main.tabs.home.brewery

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
import com.example.beerservice.databinding.FragmentBreweryBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class BreweryDetailsFragment() : BaseFragment(R.layout.fragment_brewery) {
    lateinit var binding: FragmentBreweryBinding
    private val args by navArgs<BreweryDetailsFragmentArgs>()
    lateinit var recycler: RecyclerView
    override val viewModel: BreweryDetailsViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreweryBinding.bind(view)

        setupBeersList()
        setupBreweryDetailsBlock()


    }


    private fun setupBeersList() {
        val adapter = BeerPagingAdapter(onBeerClickListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerBeerByBreweryDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        observeLoadState(adapter)
        observeBeers(adapter)


    }

    private fun setupBreweryDetailsBlock() {
        viewModel.getBrewery(args.breweryId)
        viewModel.brewery.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    with(binding) {
                        Glide.with(this@BreweryDetailsFragment)
                            .load(it.value.image)
                            .into(imageViewBrewery)
                        textViewBreweryDesc.text = it.value.description
                        textViewBreweryTitle.text = it.value.name
                        textViewBreweryAddress.text = it.value.city
                        textViewBreweryCity.text = it.value.city
                        setupBreweryId(it.value.id!!)
                    }

                }
                is Empty -> TODO()
            }
        }
    }


    private fun observeBeers(adapter: BeerPagingAdapter) {
        viewModel.getBeersByBreweryId()
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

    private fun setupBreweryId(id: Int) {
        viewModel.setBreweryId(id)
    }

    private val onBeerClickListener = object : OnBeerClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction =
                BreweryDetailsFragmentDirections.actionBreweryDetailsFragmentToBeerDetailsFragment(beer.id!!)
            findNavController().navigate(direction)
        }
    }

}