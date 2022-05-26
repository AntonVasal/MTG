package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin

import android.os.Bundle
import android.view.View
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.mtg.R
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.baseTypeTaskFragment.BaseTypeTaskFragment
import com.google.android.material.tabs.TabLayout

class IntegersFragment(override val typeNumber: Int = 2) : BaseTypeTaskFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewData()
    }

//    private lateinit var tabs: TabLayout;
//    private lateinit var bottomNavigation: MeowBottomNavigation
//
//
//    override fun onStart() {
//        super.onStart()
//        tabs = requireActivity().findViewById(R.id.main_tabs)
//        bottomNavigation = requireActivity().findViewById(R.id.main_bottom_navigation)
//        tabs.setBackgroundColor(resources.getColor(R.color.lottie_calculator_blue_bright, context?.theme))
//        bottomNavigation.setBackgroundColor(resources.getColor(R.color.lottie_calculator_blue_bright, context?.theme))
//    }



    private fun setViewData() {
        binding.apply {
            binding.mainCountAnim.setAnimation(R.raw.blue_light_calculator)
//            helloCard.setCardBackgroundColor(resources.getColor(R.color.count_green, context?.theme))
            addText.setBackgroundColor(resources.getColor(R.color.lottie_calculator_blue_bright, context?.theme))
            divText.setBackgroundColor(resources.getColor(R.color.lottie_calculator_blue_bright, context?.theme))
        }
    }

}