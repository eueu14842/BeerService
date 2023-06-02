package com.example.beerservice.app.screens.main.tabs.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.SearchPagerAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentSearchBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class SearchFragment : BaseFragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding

    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }
    private val navArgs by navArgs<SearchFragmentArgs>()


    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var searchPagerAdapter: SearchPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        getSearchData()
        println(viewModel)
        setupViews()
        searchPagerAdapter = SearchPagerAdapter(this, navArgs.searchBy)
        viewPager.adapter = searchPagerAdapter
    }


    private fun setupViews() {
        tabLayout = binding.tabLayoutSearch
        viewPager = binding.pagerSearch
    }

    private fun getSearchData() {
        viewModel.setSearchBy(navArgs.searchBy)
        viewModel.getSearchData()
        viewModel.searchData.observe(viewLifecycleOwner) {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                viewPager.isUserInputEnabled = false
                when (position) {
                    0 -> {
                        val badge = tab.orCreateBadge
                        badge.number = it.beer!!.size
                        tab.setText(R.string.title_slider_beer)
                    }
                    1 -> {
                        val badge = tab.orCreateBadge
                        badge.number = it.place!!.size
                        tab.setText(R.string.places)
                    }
                    2 -> {
                        val badge = tab.orCreateBadge
                        badge.number = it.brewery!!.size
                        tab.setText(R.string.title_slider_brewery)
                    }
                }
            }.attach()

        }
    }


}