package com.example.beerservice.app.screens.main.tabs.home.brewery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.R
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.BreweryPagingAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBreweryListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class BreweryListFragment : BaseFragment(R.layout.fragment_brewery_list) {

    lateinit var binding: FragmentBreweryListBinding
    lateinit var recycler: RecyclerView
    override val viewModel: BreweryListViewModel by viewModels { ViewModelFactory() }
//    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreweryListBinding.inflate(layoutInflater)


        setupBreweriesList()
        return binding.root
    }


    fun setupBreweriesList() {
        val adapter = BreweryPagingAdapter(onBreweryListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        /*     mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
                 binding.loadStateView,
                 binding.swipeRefreshLayout,
                 tryAgainAction
             )*/
        observeBreweries(adapter)
        observeLoadState(adapter)


    }

    private fun observeBreweries(adapter: BreweryPagingAdapter) {
        lifecycleScope.launch {
            viewModel.breweriesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: BreweryPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
//                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreweryListBinding.bind(view)

        recycler = binding.recyclerBrewery.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }


/*        viewModel.brewery.observe(viewLifecycleOwner) {
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
        }*/

    }


    private val onBreweryListener = object : OnBreweryClickListener {
        override fun onBreweryClick(brewery: Brewery, position: Int) {
            val direction =
                BreweryListFragmentDirections.actionBreweryListFragmentToBreweryFragment(brewery.id)
            findNavController().navigate(direction)
        }
    }


}