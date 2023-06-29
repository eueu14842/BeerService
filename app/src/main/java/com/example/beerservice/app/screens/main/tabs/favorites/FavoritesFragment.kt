package com.example.beerservice.app.screens.main.tabs.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceLocationListFragmentDirections
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentFavoritesBinding

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {
    lateinit var binding: FragmentFavoritesBinding
    override val viewModel: FavoritesViewModel by viewModels { ViewModelFactory() }
    lateinit var recycler: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setupViews()

        viewModel.getFavorite()
        getFavoritePlaces()
        observeOnNavigateToPlaceDetailsEvent()
        return binding.root
    }

    private fun getFavoritePlaces() {
        viewModel.place.observe(viewLifecycleOwner) { result ->
            result.map { places ->
                val adapter =
                    PlaceListAdapter(places, viewModel)
                recycler.adapter = adapter
            }
        }
    }

    private fun setupViews() {
        recycler = binding.recyclerFavoritePlace.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onToggleFavoriteEvent() {
        viewModel.onToggleFavoriteEvent.observeEvent(viewLifecycleOwner) {
            getFavoritePlaces()
        }
    }


    private fun observeOnNavigateToPlaceDetailsEvent(){
  /*      viewModel.onNavigateToPlaceDetails.observeEvent(viewLifecycleOwner) {
            val direction =
                PlaceLocationListFragmentDirections.actionPlaceListFragmentToPlaceDetailsFragment(
                    it
                )
            findNavController().navigate(direction)
        }*/
    }


}