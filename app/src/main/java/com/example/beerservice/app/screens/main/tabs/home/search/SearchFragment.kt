package com.example.beerservice.app.screens.main.tabs.home.search

import androidx.fragment.app.viewModels
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModels { ViewModelFactory() }
}