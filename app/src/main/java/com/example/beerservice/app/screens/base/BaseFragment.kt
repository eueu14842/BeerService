package com.example.beerservice.app.screens.base

import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.beerservice.app.screens.main.MainActivity

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    abstract val viewModel: BaseViewModel

    fun logout() {
        viewModel.logout()
        restartWithSingIn()
    }

    private fun restartWithSingIn() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}