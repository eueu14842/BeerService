package com.example.beerservice.app.screens.main.tabs.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.Error
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.beers.BeerAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListViewModel
import com.example.beerservice.app.screens.main.tabs.home.places.PlaceAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding

    override val viewModel: HomeViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val breweryRecycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
        val beerRecycler = binding.recyclerBeer.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        val placeRecycler = binding.recyclerPlaces.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
        viewModel.brewery.observe(viewLifecycleOwner) {
            when (it) {
                is Error -> TODO()
                is Pending -> TODO("прогрессБар")
                is Success -> {
                    val adapter = BreweryAdapter(it.value)
                    breweryRecycler.adapter = adapter
                }
            }
        }

        viewModel.beer.observe(viewLifecycleOwner) {
            when (it) {
                is Error -> TODO()
                is Pending -> TODO("прогрессБар")
                is Success -> {
                    val adapter = BeerAdapter(it.value)
                    beerRecycler.adapter = adapter
                }
            }
        }


        viewModel.place.observe(viewLifecycleOwner) {
            when (it) {
                is Error -> TODO()
                is Pending -> TODO("прогрессБар")
                is Success -> {
                    val adapter = PlaceAdapter(it.value)
                    placeRecycler.adapter = adapter
                }
            }
        }

        binding.showAllBreweryTextView.setOnClickListener { navigateToBreweryListEvent() }

    }


    fun navigateToBreweryListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_breweryListFragment)
    }
}