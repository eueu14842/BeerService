package com.example.beerservice.app.screens.main.tabs.home.search.breweries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.Const.SEARCH_KEY
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.BreweryAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.OnBreweryClickListener
import com.example.beerservice.app.screens.main.tabs.home.search.SearchFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentSearchBreweryListBinding
import kotlinx.coroutines.launch


class BrewerySearchListFragment : BaseFragment(R.layout.fragment_search_brewery_list) {

    private lateinit var binding: FragmentSearchBreweryListBinding
    private lateinit var recycler: RecyclerView

    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }

    private lateinit var breweryAdblockAdapter: BreweryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBreweryListBinding.inflate(layoutInflater)
        setupViews()



        observeSearchBrewery()
        return binding.root
    }

    private fun setupViews() {
        recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun observeSearchBrewery() {
        lifecycleScope.launch {
            viewModel.getSearchData()
            viewModel.setSearchBy(arguments?.getString(SEARCH_KEY, "") ?: "")
            viewModel.searchData.observe(viewLifecycleOwner) {
                val list: List<Brewery>? = it.brewery
                breweryAdblockAdapter = BreweryAdapter(list!!)
                recycler.adapter = breweryAdblockAdapter
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

    companion object {
        fun newInstance(searchBy: String) =
            BrewerySearchListFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_KEY, searchBy)
                }
            }
    }


}