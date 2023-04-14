package com.example.beerservice.app.screens.main.tabs.home.beers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeersAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBeersListBinding

class BeersListFragment : BaseFragment(R.layout.fragment_beers_list) {

    lateinit var binding: FragmentBeersListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: BeersListViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBeersListBinding.bind(view)

        recycler = binding.recyclerViewBeers.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.beers.observe(viewLifecycleOwner) {
            it.map { beers ->
                val adapter = BeersAdapter(beers)
                recycler.adapter = adapter
            }
        }
    }
}