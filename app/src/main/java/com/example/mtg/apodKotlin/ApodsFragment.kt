package com.example.mtg.apodKotlin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mtg.R
import com.example.mtg.apodKotlin.recyclerViewAdapter.ApodsRecyclerAdapter
import com.example.mtg.apodKotlin.viewModel.ApodsViewModel
import com.example.mtg.core.baseFragments.BaseStateVMFragment
import com.example.mtg.core.baseState.BaseStates
import com.example.mtg.databinding.FragmentApodBinding
import com.example.mtg.utility.extensions.gone
import com.example.mtg.utility.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class ApodsFragment(override val layoutId: Int = R.layout.fragment_apod,
                             override val viewModelClass: KClass<ApodsViewModel> = ApodsViewModel::class)
    : BaseStateVMFragment<ApodsViewModel, FragmentApodBinding, BaseStates>() {

    private val apodViewModel: ApodsViewModel by viewModels()
    private lateinit var adapter: ApodsRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apodViewModel.getApods()
        adapter = ApodsRecyclerAdapter(requireContext())
        binding.apodRecycler.adapter = adapter
        apodViewModel.apods.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onStateChanged(state: BaseStates) {
        binding.apply {
            when (state) {
                is BaseStates.ErrorState -> {
                    apodRecyclerProgressBar.gone()
                }
                is BaseStates.LoadingState -> {
                    apodRecyclerProgressBar.visible()
                }
                is BaseStates.SuccessState -> {
                    apodRecyclerProgressBar.gone()
                }
            }
        }
    }

}