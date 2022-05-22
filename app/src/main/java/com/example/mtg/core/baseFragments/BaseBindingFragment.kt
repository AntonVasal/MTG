package com.example.mtg.core.baseFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

abstract class BaseBindingFragment<Binding : ViewDataBinding> : BaseFragment() {
    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false).apply {
        binding = this
    }.root

    fun <T> LiveData<T>.observeNotNull(
        owner: LifecycleOwner = viewLifecycleOwner,
        observer: (t: T) -> Unit
    ) {
        this.observe(owner) {
            it?.let(observer)
        }
    }

}