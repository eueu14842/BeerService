package com.example.beerservice.app.screens.main.tabs.home.search.places

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.Const.SEARCH_KEY
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentSearchPlaceListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaceSearchListFragment : BaseFragment(R.layout.fragment_search_place_list) {
    private lateinit var binding: FragmentSearchPlaceListBinding
    private lateinit var recycler: RecyclerView

    override val viewModel: SearchViewModel by viewModels ()
    private lateinit var breweryAdapter: PlaceListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchPlaceListBinding.bind(view)
        setupViews()
        observeSearchPlace()
        observeOnNavigateToPlaceDetailsEvent()
        observeOnNavigateToMap()
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
        viewModel.onNavigateToPlaceDetails.observeEvent(viewLifecycleOwner) {
     /*       val direction =
                SearchFragmentDirections.actionSearchFragmentToPlaceDetailsFragment(
                    it
                )
            findNavController().navigate(direction)*/
        }
    }
    private fun observeOnNavigateToMap(){
        viewModel.onNavigateToMap.observeEvent(viewLifecycleOwner){
    /*        val direction = PlaceDetailsFragmentDirections.actionPlaceDetailsFragmentToPlaceMapFragment()
            findNavController().navigate(direction)*/
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


