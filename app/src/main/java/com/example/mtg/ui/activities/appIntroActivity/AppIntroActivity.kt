package com.example.mtg.ui.activities.appIntroActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mtg.R
import com.example.mtg.ui.activities.logActivity.LogActivity
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class AppIntroActivity : AppIntro() {

    private val sharedPreferencesHolder: SharedPreferencesHolder = SharedPreferencesHolder.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSlide(AppIntroFragment.createInstance(
                title = getString(R.string.welcome_in_mtg),
                description = getString(R.string.we_are_happy_to_see_you),
                imageDrawable = R.drawable.ic_star,
                backgroundDrawable = R.drawable.gradient_for_app,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white
        ))
        addSlide(AppIntroFragment.createInstance(
                title = getString(R.string.train),
                description = getString(R.string.we_provide_opportunity),
                imageDrawable = R.drawable.ic_mathematical,
                backgroundColorRes = R.color.blue_bright,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white
        ))
        addSlide(AppIntroFragment.createInstance(title = getString(R.string.have_a_rest),
                description = getString(R.string.for_you_we_made),
                imageDrawable = R.drawable.ic_milky_way_svgrepo_com,
                backgroundColorRes = R.color.orange_dark,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white))
        addSlide(AppIntroFragment.createInstance(
                title = getString(R.string.compete),
                description = getString(R.string.you_can_compete),
                imageDrawable = R.drawable.ic_trophy_pixel,
                backgroundDrawable = R.drawable.gradient_for_app,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white
        ))
//        setTransformer(AppIntroPageTransformerType.Parallax(
//                titleParallaxFactor = 1.0,
//                imageParallaxFactor = -1.0,
//                descriptionParallaxFactor = 2.0
//        ))
        setImmersiveMode()
        setTransformer(AppIntroPageTransformerType.Fade)
    }


    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        sharedPreferencesHolder.setIsFirstOpening("isFirstOpening", false)
        toLogActivity()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        sharedPreferencesHolder.setIsFirstOpening("isFirstOpening", false)
        toLogActivity()
        finish()
    }

    private fun toLogActivity() {
        startActivity(Intent(this, LogActivity::class.java))
    }
}