package com.example.beerservice.app.screens.main.tabs.home.brewery

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.Empty
import com.example.beerservice.app.model.ErrorResult
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBreweryListBinding

class BreweryListFragment : BaseFragment(R.layout.fragment_brewery_list) {

    lateinit var binding: FragmentBreweryListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: BreweryListViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreweryListBinding.bind(view)

        recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }


        viewModel.brewery.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    val adapter = BreweryAdapter(
                        it.value, onBreweryListener
                    )
                    recycler.adapter = adapter
                }
                is Empty -> TODO()
            }
        }


    }


    private val onBreweryListener = object : OnBreweryClickListener {
        override fun onBreweryClick(brewery: Brewery, position: Int) {
            val direction =
                BreweryListFragmentDirections.actionBreweryListFragmentToBreweryFragment(brewery.id)
            findNavController().navigate(direction)
        }
    }


}