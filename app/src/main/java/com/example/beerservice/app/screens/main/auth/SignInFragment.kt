package com.example.beerservice.app.screens.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beerservice.R
import com.example.beerservice.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            navigateToTabs()
        }

        return binding.root
    }

    private fun navigateToTabs() {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
    }

    private fun onSignUpButtonPressed() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }
}