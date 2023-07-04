package com.example.beerservice.app.screens.main.feedback

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentCreateFeedbackBinding

class FeedbackCreateFragment : BaseFragment(R.layout.fragment_create_feedback) {
    lateinit var binding: FragmentCreateFeedbackBinding
    override val viewModel: FeedbackCreateViewModel by viewModels {ViewModelFactory()  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateFeedbackBinding.bind(view)

    }
}