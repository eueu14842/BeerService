package com.example.beerservice.app.screens.main.tabs.home.search.beers

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
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.BeersAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerClickListener
import com.example.beerservice.app.screens.main.tabs.home.search.SearchFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentSearchBeerListBinding
import kotlinx.coroutines.launch


class BeerSearchListFragment : BaseFragment(R.layout.fragment_search_beer_list) {

    private lateinit var binding: FragmentSearchBeerListBinding
    private lateinit var recycler: RecyclerView

    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }

    private lateinit var beerAdapter: BeersAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBeerListBinding.inflate(layoutInflater)

        recycler = binding.recyclerViewBeers.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        observeSearchBeer()

        return binding.root
    }

    private fun observeSearchBeer() {
        lifecycleScope.launch {
            viewModel.getSearchData()
            viewModel.setSearchBy(arguments?.getString(SEARCH_KEY, "") ?: "")
            viewModel.searchData.observe(viewLifecycleOwner) {
                val list: List<Beer>? = it.beer
                beerAdapter = BeersAdapter(list!!)
                recycler.adapter = beerAdapter
            }
        }
    }

    private val onBeerClickListener = object : OnBeerClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction =
                SearchFragmentDirections.actionSearchFragmentToBeerDetailsFragment(
                    beer.id!!
                )
            findNavController().navigate(direction)
        }
    }

    companion object {
        fun newInstance(searchBy: String) =
            BeerSearchListFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_KEY, searchBy)
                }
            }
    }


}