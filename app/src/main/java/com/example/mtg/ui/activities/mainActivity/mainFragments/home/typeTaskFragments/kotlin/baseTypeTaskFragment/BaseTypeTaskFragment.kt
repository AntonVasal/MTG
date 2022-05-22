package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.baseTypeTaskFragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mtg.R
import com.example.mtg.core.baseFragments.BaseBindingFragment
import com.example.mtg.databinding.FragmentTypeTaskBinding
import com.example.mtg.ui.activities.mainActivity.mainFragments.MainFragmentDirections

abstract class BaseTypeTaskFragment(override val layoutId: Int = R.layout.fragment_type_task) : BaseBindingFragment<FragmentTypeTaskBinding>() {

    private val action: MainFragmentDirections.ActionMainFragment2ToCountFragment = MainFragmentDirections.actionMainFragment2ToCountFragment()
    abstract val typeNumber : Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action.typeNumber = typeNumber
        initListeners()
    }

    fun initListeners() {
        binding.apply {
            addText.setOnClickListener {
                action.taskType = 1
                goToCountFragment()
            }
            divText.setOnClickListener {
                action.taskType = 4
                goToCountFragment()
            }
            subText.setOnClickListener {
                action.taskType = 3
                goToCountFragment()
            }
            multiText.setOnClickListener {
                action.taskType = 2
                goToCountFragment()
            }
        }
    }

    private fun goToCountFragment() {
        findNavController().navigate(action)
    }

}