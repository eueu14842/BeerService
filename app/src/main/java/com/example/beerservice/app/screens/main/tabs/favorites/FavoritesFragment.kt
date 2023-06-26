package com.example.beerservice.app.screens.main.tabs.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentFavoritesBinding

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {
    lateinit var binding: FragmentFavoritesBinding
    override val viewModel: FavoritesViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)

        viewModel.initialEditEvent.observeEvent(viewLifecycleOwner){

        }
    }
}