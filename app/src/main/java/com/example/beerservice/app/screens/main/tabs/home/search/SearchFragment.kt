package com.example.beerservice.app.screens.main.tabs.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.SearchPagerAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding

    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }


    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var searchPagerAdapter: SearchPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        setupViews()
        searchPagerAdapter = SearchPagerAdapter(this)
        viewPager.adapter = searchPagerAdapter
        setupMediator()
        getSearchData()
    }

    private fun setupMediator() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.isUserInputEnabled = false
            when (position) {
                0 -> tab.setText("Пиво")
                1 -> tab.setText("Места")
                2 -> tab.setText("Пивоварни")
            }
        }.attach()
    }


    private fun setupViews() {
        tabLayout = binding.tabLayoutSearch
        viewPager = binding.pagerSearch
    }

    private fun getSearchData() {
        viewModel.setSearchBy("Абр")
        viewModel.searchData.observe(viewLifecycleOwner) {
            println(it.beer?.size)
        }
    }
}