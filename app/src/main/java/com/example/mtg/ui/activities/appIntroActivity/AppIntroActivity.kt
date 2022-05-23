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
                title = "Welcome in MTG!",
                description = "We are happy to see you in our first math app! Hope that you will become a part of our math family! Enjoy!)",
                imageDrawable = R.drawable.ic_star,
                backgroundDrawable = R.drawable.gradient_for_app,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white
        ))
        addSlide(AppIntroFragment.createInstance(
                title = "Train your math skills!",
                description = "We provide you opportunity to train your counting skills by using 12 grounds which generate 48 types of task! 24/7 and absolutely free!",
                imageDrawable = R.drawable.ic_mathematical,
                backgroundColorRes = R.color.blue_bright,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white
        ))
        addSlide(AppIntroFragment.createInstance(title = "Have a rest!",
                description = "For you we specially made page with beautiful astronomy pictures and interesting facts about them which had been provided by NASA! Make breaks between counting) ",
                imageDrawable = R.drawable.ic_milky_way_svgrepo_com,
                backgroundColorRes = R.color.orange_dark,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white))
        addSlide(AppIntroFragment.createInstance(
                title = "Compete!",
                description = "You can compete with people all over the world! Become the best counter! What are you waiting?)",
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
        sharedPreferencesHolder.setIsFirstOpening("isFirstOpening",false)
        toLogActivity()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        sharedPreferencesHolder.setIsFirstOpening("isFirstOpening",false)
        toLogActivity()
        finish()
    }

    private fun toLogActivity() {
        startActivity(Intent(this,LogActivity::class.java))
    }
}