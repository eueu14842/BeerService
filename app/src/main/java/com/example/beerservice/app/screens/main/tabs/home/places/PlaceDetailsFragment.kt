package com.example.beerservice.app.screens.main.tabs.home.places

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.Const
import com.example.beerservice.app.model.Empty
import com.example.beerservice.app.model.ErrorResult
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerPagingAdapter
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentPlaceDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaceDetailsFragment : BaseFragment(R.layout.fragment_place_details) {
    lateinit var binding: FragmentPlaceDetailsBinding

    override val viewModel: PlaceDetailsViewModel by viewModels ()
    private val args by navArgs<PlaceDetailsFragmentArgs>()
    lateinit var recycler: RecyclerView
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceDetailsBinding.bind(view)

        setupBeersList()
        setupBreweryDetailsBlock()

    }

    private fun setupBeersList() {
        val adapter = BeerPagingAdapter(viewModel)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerBeerByPlaceDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            null,
            tryAgainAction
        )
        recycler.adapter = adapterWithLoadState

        observeLoadState(adapter)
        observeBeers(adapter)
//       observeOnToggleFavoriteEvent()
        observeOnNavigateBeerDetailsEvent()
        observeOnNavigateToCreateFeedback()
//        observeOnNavigateToMapEvent()

    }

    private fun setupBreweryDetailsBlock() {
        viewModel.getPlace(args.placeId)
        viewModel.place.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    with(binding.placeView) {
                        Glide.with(this@PlaceDetailsFragment)
                            .load(it.value.image)
                            .into(imageViewPlace)

                        textViewPlaceDesc.text = it.value.description
                        textViewPlaceTitle.text = it.value.name
                        textViewPlaceCity.text = it.value.city

                        heartImageView.setOnClickListener(viewModel)
                        textViewShowPlaceOnMap.setOnClickListener(viewModel)
                        textViewShowPlaceOnMap.setOnClickListener(viewModel)
                        heartImageView.setImageResource(
                            if (it.value.setAvailabilityOfSpaceForTheUser == true) R.drawable.ic_baseline_favorite_24
                            else R.drawable.ic_baseline_favorite_border_24
                        )

                        setupPlaceId(it.value.placeId!!)
                    }
                }
                is Empty -> TODO()
            }
        }
    }

    private fun setupPlaceId(id: Int) {
        viewModel.setPlaceId(id)
    }


    private fun observeBeers(adapter: BeerPagingAdapter) {
        viewModel.getBeersByPlaceId()
        lifecycleScope.launch {
            viewModel.beersFlow?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: BeerPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }


    private fun observeOnNavigateBeerDetailsEvent() {
        viewModel.onNavigateToBeerDetails.observeEvent(viewLifecycleOwner) {
            val direction =
                PlaceDetailsFragmentDirections.actionPlaceDetailsFragment2ToBeerDetailsFragment2(it)
            findNavController().navigate(direction)
        }
    }

    private fun observeOnNavigateToCreateFeedback() {
        viewModel.onNavigateToCreateFeedback.observeEvent(viewLifecycleOwner) {
            val direction =
                PlaceDetailsFragmentDirections.actionPlaceDetailsFragment2ToFeedbackCreateFragment2(it)
            findNavController().navigate(direction)
        }
    }

    private fun observeOnNavigateToMapEvent() {
        viewModel.onNavigateToMap.observeEvent(viewLifecycleOwner) { location ->
            val direction: NavDirections =
                PlaceDetailsFragmentDirections.actionPlaceDetailsFragment2ToPlaceMapFragment2()
            direction.arguments.putDouble(Const.LONGITUDE, location.geoLon!!)
            direction.arguments.putDouble(Const.LATITUDE, location.geoLat!!)
            findNavController().navigate(direction)
        }
    }

    private fun observeOnToggleFavoriteEvent() {
        viewModel.onToggleFavoriteEvent.observeEvent(viewLifecycleOwner) {
            if (it) setupBreweryDetailsBlock()
        }
    }

}

