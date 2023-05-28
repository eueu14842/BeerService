package com.example.beerservice.app.screens.main.tabs.home.beers

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
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerClickListener
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBeersListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class BeersListFragment : BaseFragment(R.layout.fragment_beers_list) {

    lateinit var binding: FragmentBeersListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: BeersListViewModel by viewModels { ViewModelFactory() }
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeersListBinding.inflate(layoutInflater)

        setupBeersList()
        return binding.root
    }

    private fun setupBeersList() {
        val adapter = BeerPagingAdapter(onBeerClickListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)
        recycler = binding.recyclerViewBeers.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        observeBeers(adapter)
        observeLoadState(adapter)


    }

    private fun observeBeers(adapter: BeerPagingAdapter) {
        lifecycleScope.launch {
            viewModel.beersFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: BeerPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
//                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private val onBeerClickListener = object : OnBeerClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction =
                BeersListFragmentDirections.actionBeersListFragmentToBeerFragment(beer.id!!)
            findNavController().navigate(direction)
        }
    }


}