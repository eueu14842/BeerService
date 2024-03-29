package com.example.beerservice.app.screens.main.tabs.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerAdblockAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerAdblockClickListener
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryDetailsFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.brewery.OnBreweryClickListener
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.PlaceAdblockAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeResult
import com.example.beerservice.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding

    override val viewModel: HomeViewModel by viewModels { ViewModelFactory() }

    lateinit var breweryRecycler: RecyclerView
    lateinit var beerRecycler: RecyclerView
    lateinit var placeRecycler: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initViews()
        observeBreweryAdblock()
        observeBeerAdblock()
        observePlaceAdblock()

        binding.showAllBreweryTextView.setOnClickListener { navigateToBreweryListEvent() }
        binding.showAllBeerTextView.setOnClickListener { navigateToBeerListEvent() }
        binding.showAllStoresTextView.setOnClickListener { navigateToPlaceListEvent() }


    }

    private fun initViews() {
        with(binding) {
            recyclerBrewery.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
            recyclerBeer.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
            recyclerPlaces.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
        }
        breweryRecycler = binding.recyclerBrewery
        beerRecycler = binding.recyclerBeer
        placeRecycler = binding.recyclerPlaces
    }


    private fun observeBreweryAdblock() {
        binding.resultViewState.setTryAgainAction { println("try again") }
        viewModel.brewery.observeResult(this, binding.root, binding.resultViewState) { breweries ->
            val adapter = BreweryAdapter(breweries, onBreweryClickListener)
            breweryRecycler.adapter = adapter
        }
    }

    private fun observeBeerAdblock() {
        binding.resultViewState.setTryAgainAction { println("try again") }
        viewModel.beer.observeResult(this, binding.root, binding.resultViewState) { beers ->
            val adapter = BeerAdblockAdapter(beers, onBeerAdblockClickListener)
            beerRecycler.adapter = adapter
        }
    }

    private fun observePlaceAdblock() {
        viewModel.place.observeResult(this, binding.root, binding.resultViewState) { stores ->
            val adapter = PlaceAdblockAdapter(stores)
            placeRecycler.adapter = adapter
        }
    }


    private val onBreweryClickListener = object : OnBreweryClickListener {
        override fun onBreweryClick(brewery: Brewery, position: Int) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToBreweryDetailsFragment(brewery.id)
            findNavController().navigate(direction)
        }
    }


    private val onBeerAdblockClickListener = object : OnBeerAdblockClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction = HomeFragmentDirections.actionHomeFragmentToBeerDetailsFragment(beer.id)
            findNavController().navigate(direction)
        }
    }


    private fun navigateToBreweryListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_breweryListFragment)
    }

    private fun navigateToBeerListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_beersListFragment)
    }

    private fun navigateToPlaceListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_placeFragment)
    }
}