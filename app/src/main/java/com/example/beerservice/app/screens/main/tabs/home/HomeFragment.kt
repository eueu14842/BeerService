package com.example.beerservice.app.screens.main.tabs.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import android.widget.SearchView.OnSuggestionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerAdblockAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerAdblockClickListener
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.OnBreweryClickListener
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.OnPlaceClickListener
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


        val search = binding.searchView.apply {
            setOnCloseListener(onCloseSearchListener)
            setOnQueryTextListener(onQueryTextListener)
        }


        binding.showAllBreweryTextView.setOnClickListener { navigateToBreweryListEvent() }
        binding.showAllBeerTextView.setOnClickListener { navigateToBeerListEvent() }
        binding.showAllStoresTextView.setOnClickListener { navigateToPlaceListEvent() }


    }

    private val onCloseSearchListener = OnCloseListener {
        Toast.makeText(requireContext(), "onClose", Toast.LENGTH_SHORT).show()
        true
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            navigateToSearchEvent()
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            return false
        }
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
            val adapter = PlaceAdblockAdapter(stores, onPlaceClickListener)
            placeRecycler.adapter = adapter
        }
    }


    private val onBreweryClickListener = object : OnBreweryClickListener {
        override fun onBreweryClick(brewery: Brewery, position: Int) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToBreweryDetailsFragment(brewery.id!!)
            findNavController().navigate(direction)
        }
    }


    private val onBeerAdblockClickListener = object : OnBeerAdblockClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToBeerDetailsFragment(beer.id!!)
            findNavController().navigate(direction)
        }
    }


    private val onPlaceClickListener = object : OnPlaceClickListener {
        override fun onPlaceClick(place: Place, position: Int) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToPlaceDetailsFragment(place.placeId!!)
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
        findNavController().navigate(R.id.action_homeFragment_to_placeListFragment)
    }

    private fun navigateToSearchEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_search)
    }
}