package com.example.beerservice.app.screens.main.tabs.home.search.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.Const.SEARCH_KEY
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.SearchFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceLocationListFragmentDirections
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentSearchPlaceListBinding
import kotlinx.coroutines.launch


class PlaceSearchListFragment : BaseFragment(R.layout.fragment_search_place_list) {
    private lateinit var binding: FragmentSearchPlaceListBinding
    private lateinit var recycler: RecyclerView

    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }
    private lateinit var breweryAdapter: PlaceListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchPlaceListBinding.bind(view)
        setupViews()
        observeSearchPlace()
        observeOnNavigateToPlaceDetailsEvent()
    }

    private fun observeSearchPlace() {
        lifecycleScope.launch {
            viewModel.getSearchData()
            viewModel.setSearchBy(arguments?.getString(SEARCH_KEY, "") ?: "")
            viewModel.searchData.observe(viewLifecycleOwner) {
                val list = it.place
                breweryAdapter =
                    PlaceListAdapter(list!!, viewModel)
                recycler.adapter = breweryAdapter
            }
        }
    }

    private fun setupViews() {
        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun observeOnNavigateToPlaceDetailsEvent() {
        viewModel.onNavigateToMapPlaceDetails.observeEvent(viewLifecycleOwner) {
            val direction =
                SearchFragmentDirections.actionSearchFragmentToPlaceDetailsFragment(
                    it
                )
            findNavController().navigate(direction)
        }
    }

    companion object {
        fun newInstance(searchBy: String) =
            PlaceSearchListFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_KEY, searchBy)
                }
            }
    }


}


