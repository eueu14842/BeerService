package com.example.beerservice.app.screens.main.tabs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentProfileBinding
import kotlin.math.log

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    override val viewModel: ProfileViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)


        binding.buttonLogout.setOnClickListener { logout() }
        observeProfileTest()
    }

    fun observeProfile() {
        viewModel.getProfile()
        viewModel.profile.observe(viewLifecycleOwner) {
            it.map {
                with(binding) {
                    Glide.with(requireContext()).load(it.image).into(imageViewUserPhoto)
                    textViewUsername.text = it.userName
                    textViewUserLocation.text = it.country
                }
            }
        }
    }

    private fun observeProfileTest() {
        val user = viewModel.accountsRepository.getAccount()
        with(binding) {
            user.map {
                Glide.with(requireContext()).load(it.image).into(imageViewUserPhoto)
                textViewUsername.text = it.userName
                textViewUserLocation.text = it.country
            }
        }
    }


}