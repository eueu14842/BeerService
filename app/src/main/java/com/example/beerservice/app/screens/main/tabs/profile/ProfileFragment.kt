package com.example.beerservice.app.screens.main.tabs.profile

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    override val viewModel: ProfileViewModel by viewModels { ViewModelFactory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.buttonEditProfile.setOnClickListener { onEditProfileButtonPressed() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonLogout.setOnClickListener { logout() }
        observeProfile()
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


}