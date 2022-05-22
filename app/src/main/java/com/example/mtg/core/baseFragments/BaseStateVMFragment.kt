package com.example.mtg.core.baseFragments

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.mtg.core.baseState.BaseState
import com.example.mtg.core.baseViewModel.BaseStateViewModel
import kotlin.reflect.KClass

abstract class BaseStateVMFragment<VM : BaseStateViewModel<S>, B : ViewDataBinding, S : BaseState> :
    BaseBindingFragment<B>() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: KClass<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.state.observeNotNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { state ->
                onStateChanged(state)
            }
        }
    }

    protected abstract fun onStateChanged(state: S)

    protected open fun initViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass.javaObjectType]
    }
}