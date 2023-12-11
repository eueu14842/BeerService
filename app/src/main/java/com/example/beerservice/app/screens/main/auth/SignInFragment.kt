package com.example.beerservice.app.screens.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.beerservice.R
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding

    val viewModel: SignInViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.signUpButton.setOnClickListener { onSignUpButtonPressed() }

        observeState()
        observeNavigateToTabsEvent()
        observeShowAuthErrorMessageEvent()
        observeShowConnectionErrorMessageEvent()
        return binding.root
    }


    private fun onSignInButtonPressed() {
        viewModel.signIn(
            login = binding.loginEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.loginTextInput.error = if (it.emptyLoginError) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if(it.emptyPasswordError) getString(R.string.field_is_empty)else null
    }

    private fun observeShowAuthErrorMessageEvent() =
        viewModel.showAuthToastEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

    private fun observeShowConnectionErrorMessageEvent() =
        viewModel.showConnectionErrorToastEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

    private fun observeNavigateToTabsEvent() =
        viewModel.navigateToTabsEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
        }


    private fun onSignUpButtonPressed() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }
}