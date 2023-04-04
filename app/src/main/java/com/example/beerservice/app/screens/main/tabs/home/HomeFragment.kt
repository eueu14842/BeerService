package com.example.beerservice.app.screens.main.tabs.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import com.example.beerservice.R
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListFragment
import com.example.beerservice.databinding.FragmentBreweryListBinding
import com.example.beerservice.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.showAllBreweryTextView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_breweryListFragment)
        }

    }
}