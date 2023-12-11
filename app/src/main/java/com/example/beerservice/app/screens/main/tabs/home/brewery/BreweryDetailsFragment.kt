package com.example.beerservice.app.screens.main.tabs.home.brewery

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.Empty
import com.example.beerservice.app.model.ErrorResult
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.BeersListFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerClickListener
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentBreweryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
@AndroidEntryPoint
class BreweryDetailsFragment() : BaseFragment(R.layout.fragment_brewery) {
    lateinit var binding: FragmentBreweryBinding
    private val args by navArgs<BreweryDetailsFragmentArgs>()
    lateinit var recycler: RecyclerView
    override val viewModel: BreweryDetailsViewModel by viewModels ()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreweryBinding.bind(view)

        setupBeersList()
        setupBreweryDetailsBlock()
        observeOnNavigateBeerDetailsEvent()
        observeOnNavigateToCreateFeedback()

    }

    private fun setupBeersList() {
        val adapter = BeerPagingAdapter(viewModel)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerBeerByBreweryDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            null,
            tryAgainAction
        )
        observeLoadState(adapter)
        observeBeers(adapter)

    }

    private fun setupBreweryDetailsBlock() {
        viewModel.getBrewery(args.breweryId)
        viewModel.brewery.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    with(binding.breweryView) {
                        Glide.with(this@BreweryDetailsFragment)
                            .load(it.value.image)
                            .into(ivBreweryImage)
                        tvBreweryName.text = it.value.name
                        tvBreweryDescription.text = it.value.description
                        tvBreweryCity.text = it.value.city
                        tvBreweryType.text = it.value.type
                        setupBreweryId(it.value.id!!)
                    }

                }
                is Empty -> TODO()
            }
        }
    }


    private fun observeBeers(adapter: BeerPagingAdapter) {
        viewModel.getBeersByBreweryId()
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

    private fun setupBreweryId(id: Int) {
        viewModel.setBreweryId(id)
    }


    private fun observeOnNavigateBeerDetailsEvent() {
        viewModel.onNavigateToBeerDetails.observeEvent(viewLifecycleOwner) {
            val direction =
                BreweryDetailsFragmentDirections.actionBreweryDetailsFragmentToBeerDetailsFragment(
                    it
                )
            findNavController().navigate(direction)
        }
    }

    private fun observeOnNavigateToCreateFeedback() {
        viewModel.onNavigateToBeerCreateFeedback.observeEvent(viewLifecycleOwner) {
            val direction =
                BreweryDetailsFragmentDirections.actionBreweryDetailsFragmentToFeedbackCreateFragment(
                    it
                )
            findNavController().navigate(direction)
        }
    }


}