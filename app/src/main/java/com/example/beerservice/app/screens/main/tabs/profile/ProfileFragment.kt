package com.example.beerservice.app.screens.main.tabs.profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.DefaultLoadStateAdapter
import com.example.beerservice.app.screens.base.TryAgainAction
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.FeedbackForBeerPagingAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnFeedbackClickListener
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.SearchPagerAdapter
import com.example.beerservice.app.screens.main.tabs.profile.adapters.FeedbackForUserPagingAdapter
import com.example.beerservice.app.screens.main.tabs.profile.adapters.ProfilePagerAdapter
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder
    lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        observeProfile()
        setupFeedbackList()
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit_profile -> onEditProfileButtonPressed()
        }
        return true
    }

    private fun observeProfile() {
        val user = viewModel.accountsRepository.getAccount()
        with(binding) {
            user.map {
                Glide.with(requireContext()).load(it.image).into(imageViewUserPhoto)
                textViewUsername.text = it.userName
                textViewUserLocation.text = it.country
            }
        }
    }

    private fun onEditProfileButtonPressed() {
        val directions = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        findNavController().navigate(directions)
    }

    private fun setupFeedbackList() {
        val adapter = FeedbackForUserPagingAdapter()
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState: ConcatAdapter = adapter.withLoadStateFooter(footerAdapter)

        recycler = binding.recyclerFeedback.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recycler.adapter = adapterWithLoadState

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            null,
            tryAgainAction
        )
        observeFeedbacks(adapter)
        observeLoadState(adapter)
    }

    private fun observeFeedbacks(adapter: FeedbackForUserPagingAdapter) {
        lifecycleScope.launch {
            viewModel.getBeerByUserId()
            viewModel.feedback?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: FeedbackForUserPagingAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }


}