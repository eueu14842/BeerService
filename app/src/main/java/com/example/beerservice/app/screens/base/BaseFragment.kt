package com.example.beerservice.app.screens.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceViewModel
import com.example.beerservice.app.utils.ViewModelFactory

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    abstract val viewModel: BaseViewModel


}