package com.example.beerservice.app.screens.main.tabs.places.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.places.adapters.OnPlaceClickListener
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlaceListBinding
import com.example.beerservice.databinding.FragmentPlaceLocationListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class PlaceLocationListFragment : BaseFragment(R.layout.fragment_place_location_list) {
    lateinit var binding: FragmentPlaceLocationListBinding
    lateinit var recycler: RecyclerView

    override val viewModel: PlaceViewModel by viewModels { ViewModelFactory() }

    private lateinit var viewModelPlace: PlaceViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceLocationListBinding.inflate(layoutInflater)
        setupViews()
        viewModelPlace = ViewModelProvider(requireActivity())[PlaceViewModel::class.java]

        observePlaces()
        return binding.root
    }


    private fun observePlaces() {
        lifecycleScope.launch {
            viewModelPlace.location.observe(viewLifecycleOwner) { location ->
                viewModelPlace.getPlaces(location.lat, location.lon, location.rad)
                viewModelPlace.place.observe(viewLifecycleOwner) { result ->
                    result.map { places ->
                        val adapter =
                            PlaceListAdapter(places, getCurrentListenerDependsOnDestination())
                        recycler.adapter = adapter
                    }
                }
            }
        }
    }

    private fun getCurrentListenerDependsOnDestination(): OnPlaceClickListener {
        return if (findNavController().currentDestination?.parent?.id == R.id.place) onPlaceClickListenerFromPlaces
        else onPlaceClickListenerFromHome
    }

    private val onPlaceClickListenerFromPlaces = object : OnPlaceClickListener {
        override fun onPlaceClick(place: Place, position: Int) {
            val direction =
                PlaceContainerFragmentDirections.actionPlaceFragmentToPlaceDetailsFragment(place.placeId!!)
            findNavController().navigate(direction)
        }
    }

    private val onPlaceClickListenerFromHome = object : OnPlaceClickListener {
        override fun onPlaceClick(place: Place, position: Int) {
            val direction =
                PlaceLocationListFragmentDirections.actionPlaceListFragmentToPlaceDetailsFragment(
                    place.placeId!!
                )
            findNavController().navigate(direction)
        }
    }

    private fun setupViews() {
        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }




}


