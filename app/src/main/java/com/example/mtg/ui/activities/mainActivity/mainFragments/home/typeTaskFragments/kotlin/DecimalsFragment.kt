package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin

import android.os.Bundle
import android.view.View
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.mtg.R
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.baseTypeTaskFragment.BaseTypeTaskFragment
import com.google.android.material.tabs.TabLayout

class DecimalsFragment(override val typeNumber: Int = 3) : BaseTypeTaskFragment() {

//    private lateinit var tabs:TabLayout;
//    private lateinit var bottomNavigation: MeowBottomNavigation
//
//    override fun onStart() {
//        super.onStart()
//        tabs = requireActivity().findViewById(R.id.main_tabs)
//        bottomNavigation = requireActivity().findViewById(R.id.main_bottom_navigation)
//        tabs.setBackgroundColor(resources.getColor(R.color.lottie_calculator_orange, context?.theme))
//        bottomNavigation.setBackgroundColor(resources.getColor(R.color.lottie_calculator_orange, context?.theme))
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewData()
    }

    private fun setViewData() {
        binding.apply {
            addText.setBackgroundColor(resources.getColor(R.color.lottie_calculator_orange, context?.theme))
            divText.setBackgroundColor(resources.getColor(R.color.lottie_calculator_orange, context?.theme))
            binding.mainCountAnim.setAnimation(R.raw.yellow_calculator)
        }
    }

}