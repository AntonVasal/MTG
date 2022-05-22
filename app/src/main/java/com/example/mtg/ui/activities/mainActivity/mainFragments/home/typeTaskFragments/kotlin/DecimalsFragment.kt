package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin

import android.os.Bundle
import android.view.View
import com.example.mtg.R
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.baseTypeTaskFragment.BaseTypeTaskFragment

class DecimalsFragment(override val typeNumber: Int = 3) : BaseTypeTaskFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewData()
    }

    private fun setViewData() {
        binding.apply {
            helloCard.setCardBackgroundColor(resources.getColor(R.color.count_yellow, context?.theme))
            addText.setImageResource(R.drawable.plus_yellow)
            subText.setImageResource(R.drawable.minus_yellow)
            divText.setImageResource(R.drawable.division_yellow)
            multiText.setImageResource(R.drawable.minus_yellow)
        }
    }

}