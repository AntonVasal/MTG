package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin

import android.os.Bundle
import android.view.View
import com.example.mtg.R
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.baseTypeTaskFragment.BaseTypeTaskFragment

class IntegersFragment(override val typeNumber: Int = 2) : BaseTypeTaskFragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewData()
    }

    private fun setViewData() {
        binding.apply {
            helloCard.setCardBackgroundColor(resources.getColor(R.color.count_green, context?.theme))
            addText.setImageResource(R.drawable.ic_plus_green)
            subText.setImageResource(R.drawable.ic_minus_green)
            divText.setImageResource(R.drawable.ic_div_green)
            multiText.setImageResource(R.drawable.ic_multi_green)
        }
    }

}