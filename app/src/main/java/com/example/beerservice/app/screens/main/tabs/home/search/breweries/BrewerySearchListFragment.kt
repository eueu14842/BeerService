package com.example.beerservice.app.screens.main.tabs.home.search.breweries

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
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeersAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListViewModel
import com.example.beerservice.app.screens.main.tabs.home.brewery.OnBreweryClickListener
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.BreweryPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.OnBreweryPagedClickListener
import com.example.beerservice.app.screens.main.tabs.home.search.SearchFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBreweryListBinding
import com.example.beerservice.databinding.FragmentSearchBreweryListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class BrewerySearchListFragment : BaseFragment(R.layout.fragment_search_brewery_list) {

    lateinit var binding: FragmentSearchBreweryListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: BreweryListViewModel by viewModels { ViewModelFactory() }
    private val viewModelSearch: SearchViewModel by viewModels { ViewModelFactory() }

    lateinit var breweryAdapter: BreweryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBreweryListBinding.inflate(layoutInflater)

        recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        observeSearchBrewery()
        return binding.root
    }

    private fun observeSearchBrewery() {
        lifecycleScope.launch {
            viewModelSearch.searchData.observe(viewLifecycleOwner) {
                val list: List<Brewery>? = it.brewery
                breweryAdapter = BreweryAdapter(list!!, onBreweryListener)
                recycler.adapter = breweryAdapter
            }
        }
    }

    private val onBreweryListener = object : OnBreweryClickListener {
        override fun onBreweryClick(brewery: Brewery, position: Int) {
            val direction =
                SearchFragmentDirections.actionSearchFragmentToBreweryDetailsFragment(brewery.id!!)
            findNavController().navigate(direction)
        }
    }


}