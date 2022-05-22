package com.example.mtg.apodKotlin

import com.example.mtg.R
import com.example.mtg.core.baseFragments.BaseStateVMFragment
import com.example.mtg.core.baseState.BaseStates
import com.example.mtg.databinding.FragmentApodBinding
import kotlin.reflect.KClass

class ApodsFragment(override val layoutId: Int = R.layout.fragment_apod,
                    override val viewModelClass: KClass<ApodsViewModel> = ApodsViewModel::class)
    : BaseStateVMFragment<ApodsViewModel, FragmentApodBinding, BaseStates>() {


    override fun onStateChanged(state: BaseStates) {
        when (state) {
            is BaseStates.ErrorState ->
                TODO()
            is BaseStates.LoadingState ->
                TODO()
            is BaseStates.SuccessState -> binding.apodRecycler.adapter = context?.let { ApodsRecyclerAdapter(it,R.layout.item_apod_recycler,ArrayList()) }

        }



    }

}