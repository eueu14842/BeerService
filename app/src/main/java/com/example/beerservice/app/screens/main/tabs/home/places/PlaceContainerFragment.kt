package com.example.beerservice.app.screens.main.tabs.home.places

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.PlaceCollectionAdapter
import com.example.beerservice.databinding.FragmentPlacesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PlaceContainerFragment : Fragment(R.layout.fragment_places) {
    lateinit var binding: FragmentPlacesBinding
    private lateinit var placesCollectionAdapter: PlaceCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlacesBinding.bind(view)

        placesCollectionAdapter = PlaceCollectionAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = placesCollectionAdapter
        tabLayout = binding.tabLayout

        prepareMediator()

    }

    private fun prepareMediator() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) tab.setText(R.string.place_map)
            else tab.setText(R.string.place_list)
        }.attach()
    }

}