package com.example.beerservice.app.screens.main.tabs.home.places

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
import com.example.beerservice.app.Const.LATITUDE
import com.example.beerservice.app.Const.LONGITUDE
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentPlaceListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class PlacePagedListFragment : BaseFragment(R.layout.fragment_place_list) {
    lateinit var binding: FragmentPlaceListBinding
    lateinit var recycler: RecyclerView

    override val viewModel: PlacePagedViewModel by viewModels { ViewModelFactory() }

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceListBinding.inflate(layoutInflater)

        setupViews()
        setupPagedPlaceList()
        observeOnNavigateToPlaceDetailsEvent()
        observeOnNavigateToMapEvent()
        return binding.root
    }

    private fun setupViews() {
        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupPagedPlaceList() {
        val adapter = PlacePagingAdapter(viewModel)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        recycler.adapter = adapterWithLoadState
        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            null,
            tryAgainAction
        )
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
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun observeOnNavigateToPlaceDetailsEvent() {
        viewModel.onNavigateToPlaceDetails.observeEvent(viewLifecycleOwner) {
            val direction =
                PlacePagedListFragmentDirections.actionPlaceListFragmentToPlaceDetailsFragment(it)
            findNavController().navigate(direction)
        }
    }

    private fun observeOnNavigateToMapEvent() {
        viewModel.onNavigateToMap.observeEvent(viewLifecycleOwner) { location ->
            val direction =
                PlacePagedListFragmentDirections.actionPlaceListFragmentToPlaceMapFragment()
            direction.arguments.putDouble(LONGITUDE, location.geoLon!!)
            direction.arguments.putDouble(LATITUDE, location.geoLat!!)
            findNavController().navigate(direction)
        }
    }

}


