package com.example.beerservice.app.screens.main.tabs.home.beers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.Empty
import com.example.beerservice.app.model.ErrorResult
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.FeedbackForBeerPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnFeedbackClickListener
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentBeerDetailsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class BeerDetailsFragment : BaseFragment(R.layout.fragment_beer_details) {
    lateinit var binding: FragmentBeerDetailsBinding
    override val viewModel: BeerViewModel by viewModels { ViewModelFactory() }
    private val navArgs by navArgs<BeerDetailsFragmentArgs>()
    lateinit var recycler: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBeerDetailsBinding.bind(view)
        setupBeerDetailsBlock()
       setupFeedbackList()

    }


    fun setupFeedbackList() {
        val adapter = FeedbackForBeerPagingAdapter(onFeedbackClickListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerFeedback.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState


        observeFeedbacks(adapter)
        observeLoadState(adapter)

    }

    private fun setupBeerDetailsBlock() {
        viewModel.getBeerById(navArgs.beerId)
        viewModel.beer.observe(viewLifecycleOwner) {
            when (it) {
                is ErrorResult -> TODO()
                is Pending -> ""
                is Success -> {
                    with(binding) {
                        Glide.with(this@BeerDetailsFragment)
                            .load(it.value.image)
                            .into(imageViewBeerDetails)
                        textViewBeerDescription.text = it.value.description
                        textViewBeerTitle.text = it.value.name

                        setBeerId(it.value.id!!)
                    }

                }
                is Empty -> TODO()
            }
        }
    }

    private fun observeFeedbacks(adapter: FeedbackForBeerPagingAdapter) {
        viewModel.getFeedbackByBeerId()
        lifecycleScope.launch {
            viewModel.feedback?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }


    private fun observeLoadState(adapter: FeedbackForBeerPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
//                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun setBeerId(id: Int) {
        viewModel.setBeerId(id)
    }


    private val onFeedbackClickListener = object : OnFeedbackClickListener {
        override fun onFeedbackClick(feedback: FeedbackBeer, position: Int) {
            Toast.makeText(
                requireContext(),
                "Feedback from : ${feedback.userName}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}