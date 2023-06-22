package com.example.beerservice.app.screens.main.tabs.home.search.places

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
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.SearchFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.OnPlaceClickListener
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentSearchPlaceListBinding
import kotlinx.coroutines.launch


class PlaceSearchListFragment : BaseFragment(R.layout.fragment_search_place_list) {
    private lateinit var binding: FragmentSearchPlaceListBinding
    private lateinit var recycler: RecyclerView

    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }
    private lateinit var breweryAdapter: PlaceListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPlaceListBinding.inflate(layoutInflater)
        setupViews()
        observeSearchPlace()



        return binding.root
    }

    private fun observeSearchPlace() {
        lifecycleScope.launch {
            viewModel.getSearchData()
            viewModel.setSearchBy(arguments?.getString(SEARCH_KEY, "") ?: "")
            viewModel.searchData.observe(viewLifecycleOwner) {
                val list = it.place
                breweryAdapter = PlaceListAdapter(list!!, onPlaceClickListenerFromHome)
                recycler.adapter = breweryAdapter
            }
        }
    }

    private fun setupViews() {
        recycler = binding.recyclerPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private val onPlaceClickListenerFromHome = object : OnPlaceClickListener {
        override fun onPlaceClick(place: Place, position: Int) {
            val direction =
                SearchFragmentDirections.actionSearchFragmentToPlaceDetailsFragment(place.placeId!!)
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


