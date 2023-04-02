package com.example.beerservice.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.beerservice.app.screens.main.auth.SignInViewModel

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel() as T
    }
}