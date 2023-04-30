package com.example.beerservice.app.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.beerservice.R
import com.example.beerservice.app.model.*
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.databinding.PartDefaultLoadStateBinding

class ResultStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: PartDefaultLoadStateBinding
    private var tryAgainAction: (() -> Unit)? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_default_load_state, this, true)
        binding = PartDefaultLoadStateBinding.bind(this)

    }

    fun setTryAgainAction(action: () -> Unit) {
        this.tryAgainAction = action
    }


    fun <T> setResult(fragment: BaseFragment, result: ResultState<T>) {
        binding.messageTextView.isVisible = result is ErrorResult<*>
        binding.tryAgainButton.isVisible = result is ErrorResult<*>
        binding.progressBar.isVisible = result is Pending<*>
        if (result is ErrorResult) {
            Log.e(javaClass.simpleName, "Error", result.error)
            val message = when (result.error) {
                is ConnectionException -> context.getString(R.string.connection_error)
                is AuthException -> context.getString(R.string.auth_error)
                is BackendException -> result.error.message
                else -> context.getString(R.string.internal_error)
            }
            binding.messageTextView.text = message
            if (result.error is AuthException) {
                renderLogoutButton(fragment)
            } else {
                renderTryAgainButton()
            }
        }
    }


    private fun renderLogoutButton(fragment: BaseFragment) {
        binding.tryAgainButton.setOnClickListener {
            //логаут из сессии
        }
        binding.tryAgainButton.setText(R.string.action_logout)
    }


    private fun renderTryAgainButton() {
        binding.tryAgainButton.setOnClickListener { tryAgainAction?.invoke() }
        binding.tryAgainButton.setText(R.string.action_try_again)
    }
}