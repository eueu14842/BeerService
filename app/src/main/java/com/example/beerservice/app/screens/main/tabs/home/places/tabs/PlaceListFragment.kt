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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class PlaceListFragment : BaseFragment(R.layout.fragment_place_list) {
    lateinit var binding: FragmentPlaceListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: PlaceViewModel by viewModels { ViewModelFactory() }
//    private val navArgs by navArgs<PlaceListFragmentArgs>()

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

    //    43.592918, 39.728160
    private fun observePlaces() {
        lifecycleScope.launch {
            viewModel.location.observe(viewLifecycleOwner) {

            }
            viewModel.getPlaces(43.592918, 39.728160, 5.0)
            viewModel.place.observe(viewLifecycleOwner) {
                it.map { places ->
                    val adapter = PlaceListAdapter(places, onPlaceClickListener)
                    recycler.adapter = adapter
                }
            }
        }
    }

    private fun setLocation(lat: Double, lon: Double) {
        viewModel.setPlacesLocation(lat, lon)
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

    fun setupPagedPlaceList() {
        val adapter = PlacePagingAdapter(onPlaceClickListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        observePagedPlaces(adapter)
        observeLoadState(adapter)
    }

    private fun observePagedPlaces(adapter: PlacePagingAdapter) {
        lifecycleScope.launch {
            viewModel.placesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: PlacePagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
//                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }


}


