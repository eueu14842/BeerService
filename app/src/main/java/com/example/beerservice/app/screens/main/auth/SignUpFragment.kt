package com.example.beerservice.app.screens.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.databinding.FragmentSignUpBinding

class SignUpFragment() : BaseFragment(R.layout.fragment_sign_up) {
    lateinit var binding: FragmentSignUpBinding
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }
}