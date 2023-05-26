package com.example.beerservice.app.screens.main.tabs.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlaceContainerBinding
import com.example.beerservice.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding
    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        getSearchData()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                // Handle tab select
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    private fun getSearchData() {
        viewModel.getSearchData("Абр")
        viewModel.searchData.observe(viewLifecycleOwner) {
            println(it.beer?.size)
        }
    }
}