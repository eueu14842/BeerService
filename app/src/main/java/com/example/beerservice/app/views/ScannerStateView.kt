package com.example.beerservice.app.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.beerservice.R
import com.example.beerservice.app.model.*
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.databinding.PartScannerStateBinding

class ScannerStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: PartScannerStateBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_scanner_state, this, true)
        binding = PartScannerStateBinding.bind(this)
    }

    fun <T> setResult(fragment: BaseFragment, result: ResultState<T>) {
        binding.scannerView.isVisible = result is Success<*>
        binding.close.isVisible = result is Success<*>

        binding.close.setOnClickListener {
            fragment.onStart()
            binding.scannerView.visibility = View.GONE
            binding.close.visibility = View.GONE
        }
    }


}