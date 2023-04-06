package com.example.beerservice.app.screens.main.tabs.home.brewery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beerservice.R
import com.example.beerservice.app.model.Error
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBreweryListBinding

class BreweryListFragment : BaseFragment(R.layout.fragment_brewery_list) {
    lateinit var binding: FragmentBreweryListBinding

    override val viewModel: BreweryListViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreweryListBinding.bind(view)

        val recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.brewery.observe(viewLifecycleOwner) {
            when (it) {
                is Error -> TODO()
                is Pending -> TODO()
                is Success -> {
                    val adapter = BreweryAdapter(it.value)
                    recycler.adapter = adapter
                }
            }
        }



    }


}