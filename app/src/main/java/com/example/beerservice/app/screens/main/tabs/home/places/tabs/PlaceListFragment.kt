package com.example.beerservice.app.screens.main.tabs.home.places.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.OnPlaceClickListener
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.PlaceListAdapter
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlaceListBinding
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class PlaceListFragment : BaseFragment(R.layout.fragment_place_list) {
    lateinit var binding: FragmentPlaceListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: PlaceViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceListBinding.inflate(layoutInflater)

        setupViews()
        observePlaces()
        return binding.root
    }

    private fun observePlaces() {
        lifecycleScope.launch {
            viewModel.location.observe(viewLifecycleOwner) { location ->
                viewModel.getPlaces(location.lat, location.lon, location.rad)
                println("rad ${location.lat}")
                viewModel.place.observe(viewLifecycleOwner) { result ->
                    result.map { places ->
                        val adapter = PlaceListAdapter(places, onPlaceClickListener)
                        recycler.adapter = adapter
                    }
                }
            }

        }
    }

    private val onPlaceClickListener = object : OnPlaceClickListener {
        override fun onPlaceClick(place: Place, position: Int) {
            val direction =
                PlaceContainerFragmentDirections.actionPlaceFragmentToPlaceDetailsFragment(place.placeId!!)
            findNavController().navigate(direction)
        }
    }

    private fun setupViews() {
        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}


