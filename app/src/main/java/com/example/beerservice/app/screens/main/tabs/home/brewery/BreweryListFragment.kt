package com.example.beerservice.app.screens.main.tabs.home.brewery

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
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.BreweryPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.OnBreweryPagedClickListener
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBreweryListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreweryListFragment : BaseFragment(R.layout.fragment_brewery_list) {

    lateinit var binding: FragmentBreweryListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: BreweryListViewModel by viewModels ()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreweryListBinding.inflate(layoutInflater)

        setupBreweriesList()
        return binding.root
    }


    private fun setupBreweriesList() {
        val adapter = BreweryPagingAdapter(onBreweryListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            null,
            tryAgainAction
        )
        observeBreweries(adapter)
        observeLoadState(adapter)

    }

    private fun observeBreweries(adapter: BreweryPagingAdapter) {
        lifecycleScope.launch {
            viewModel.breweriesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: BreweryPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }


    private val onBreweryListener = object : OnBreweryPagedClickListener {
        override fun onBreweryPagedClick(brewery: Brewery, position: Int) {
            val direction =
                BreweryListFragmentDirections.actionBreweryListFragmentToBreweryDetailsFragment(
                    brewery.id!!
                )
            findNavController().navigate(direction)
        }
    }


}