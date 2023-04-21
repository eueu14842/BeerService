package com.example.beerservice.app.screens.main.tabs.home.brewery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.beerservice.R
import com.example.beerservice.app.model.Empty
import com.example.beerservice.app.model.ErrorResult
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBreweryBinding
import com.example.beerservice.databinding.FragmentBreweryListBinding

class BreweryDetailsFragment() : BaseFragment(R.layout.fragment_brewery) {
    lateinit var binding: FragmentBreweryBinding
    private val args by navArgs<BreweryDetailsFragmentArgs>()
    override val viewModel: BreweryDetailsViewModel by viewModels { ViewModelFactory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreweryBinding.inflate(inflater, container, false)


        viewModel.getBrewery(args.breweryId)
        viewModel.brewery.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    binding.textView4.text = it.value.description
                }
                is Empty -> TODO()
            }
        }




        return binding.root
    }
}