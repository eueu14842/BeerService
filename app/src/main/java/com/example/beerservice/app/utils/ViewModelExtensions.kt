package com.example.beerservice.app.utils

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.views.ResultStateView


fun <T> LiveData<ResultState<T>>.observeResult(
    fragment: BaseFragment,
    root: View,
    resultStateView: ResultStateView,
    onSuccess: (T) -> Unit
) {
    observe(fragment.viewLifecycleOwner) { result ->
        resultStateView.setResult(fragment, result)

        val rootView: View = if (root is ScrollView)
            root.getChildAt(0)
        else
            root

        if (rootView is ViewGroup && rootView !is RecyclerView && root !is AbsListView) {
            rootView.children
                .filter { it != resultStateView }
                .forEach {
                    it.isVisible = result is Success<*>
                }
        }
        if (result is Success) onSuccess.invoke(result.value)

    }
}