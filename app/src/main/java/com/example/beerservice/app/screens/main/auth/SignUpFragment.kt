package com.example.beerservice.app.screens.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beerservice.R
import com.example.beerservice.app.model.accounts.entities.SignUpData
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment() : BaseFragment(R.layout.fragment_sign_up) {
    lateinit var binding: FragmentSignUpBinding

    override val viewModel: SignUpViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.signUpButton.setOnClickListener { onCreateAccountButtonPressed() }
        binding.signInButton.setOnClickListener { findNavController().popBackStack() }

        observeState()
        observeGoBackEvent()
        observeShowSuccessSignUpMessageEvent()
        return binding.root
    }

    private fun onCreateAccountButtonPressed() {
        val signUpData = SignUpData(
            tel = binding.telEditText.text.toString(),
            mail = binding.mailEditText.text.toString(),
            userName = binding.usernameEditText.text.toString(),
            login = binding.loginEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
        )
        viewModel.singUp(signUpData)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { state ->
        binding.signUpButton.isEnabled = state.enableViews
        binding.mailEditText.isEnabled = state.enableViews
        binding.usernameTextInput.isEnabled = state.enableViews
        binding.passwordTextInput.isEnabled = state.enableViews

        fillError(binding.mailTextInput, state.emailErrorMessageRes)
        fillError(binding.usernameTextInput, state.usernameErrorMessageRes)
        fillError(binding.passwordTextInput, state.passwordErrorMessageRes)

        binding.progressBar.visibility = if (state.showProgress) View.VISIBLE else View.INVISIBLE
    }


    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

    private fun observeShowSuccessSignUpMessageEvent() =
        viewModel.showToastEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    private fun fillError(input: TextInputLayout, @StringRes stringRes: Int) {
        if (stringRes == SignUpViewModel.NO_ERROR_MESSAGE) {
            input.error = null
            input.isErrorEnabled = false
        } else {
            input.error = getString(stringRes)
            input.isErrorEnabled = true
        }
    }
}