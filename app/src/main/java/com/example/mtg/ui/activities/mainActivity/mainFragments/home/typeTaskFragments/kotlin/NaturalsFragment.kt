package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.view.doOnPreDraw
import com.example.mtg.R
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.baseTypeTaskFragment.BaseTypeTaskFragment
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target

class NaturalsFragment(override val typeNumber: Int = 1) : BaseTypeTaskFragment() {

    private lateinit var rootContainer: View
    private val sharedPreferencesHolder: SharedPreferencesHolder = SharedPreferencesHolder.getInstance(context)

    override fun onStart() {
        super.onStart()
        rootContainer = requireActivity().findViewById(R.id.main_app_container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!sharedPreferencesHolder.isSawTutorial("isSawTutorial")) {
            showTutorial()
        }
    }

    private fun showTutorial() {
        view?.doOnPreDraw {
            val firstRoot = View.inflate(requireContext(), R.layout.layout_add_tutorial, null)
            val firstTarget = Target.Builder()
                    .setOverlay(firstRoot)
                    .build()

            val secondRoot = View.inflate(requireContext(), R.layout.layout_sub_tutorial, null)
            val secondTarget = Target.Builder()
                    .setOverlay(secondRoot)
                    .build()

            val thirdRoot = View.inflate(requireContext(), R.layout.layout_multi_tutorial, null)
            val thirdTarget = Target.Builder()
                    .setOverlay(thirdRoot)
                    .build()

            val fourthRoot = View.inflate(requireContext(), R.layout.layout_div_tutorial, null)
            val fourthTarget = Target.Builder()
                    .setOverlay(fourthRoot)
                    .build()

            val fifthRoot = View.inflate(requireContext(), R.layout.layout_numbers_tutorial, null)
            val fifthTarget = Target.Builder()
                    .setOverlay(fifthRoot)
                    .build()

            val spotlight = Spotlight.Builder(requireActivity())
                    .setTargets(
                            firstTarget,
                            secondTarget,
                            thirdTarget,
                            fourthTarget,
                            fifthTarget
                    )
                    .setBackgroundColorRes(R.color.spotlight_background)
                    .setDuration(500L)
                    .setAnimation(DecelerateInterpolator(2f))
                    .setContainer(rootContainer as ViewGroup)
                    .build()
            spotlight.start()
            spotlight.show(0)

            val nextTarget = View.OnClickListener { spotlight.next() }
            val closeSpotlight = View.OnClickListener {
                sharedPreferencesHolder.setIsSawTutorial("isSawTutorial", true)
                spotlight.finish()
            }
            firstRoot.setOnClickListener(nextTarget)
            secondRoot.setOnClickListener(nextTarget)
            thirdRoot.setOnClickListener(nextTarget)
            fourthRoot.setOnClickListener(nextTarget)
            fifthRoot.setOnClickListener(closeSpotlight)
        }
    }


}
