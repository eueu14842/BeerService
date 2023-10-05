package com.example.beerservice.app.screens.main.tabs.places.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlaceLocationListBinding
import kotlinx.coroutines.launch

class PlaceListFragment : BaseFragment(R.layout.fragment_place_location_list) {
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
                            PlaceListAdapter(places, viewModelPlace)
                        recycler.adapter = adapter
                    }
                }
            }
        }
    }

    private fun setupViews() {
        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}


