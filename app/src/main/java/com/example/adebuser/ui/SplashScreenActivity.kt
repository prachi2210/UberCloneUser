package com.example.adebuser.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.adebuser.R
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.utils.ActivityStarter
import com.example.adebuser.base.BaseActivity

class SplashScreenActivity : BaseActivity() {
    private val TAG: String = SplashScreenActivity::class.java.simpleName

    companion object {
        var SPLASH_DISPLAY_LENGTH: Long = 1600
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveToDestination()
    }

    private fun moveToDestination() {
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                userPreferences.getUserId().equals("") -> {
                    ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                        .finishAffinity()
                        .startFrom(this)
                }

                else -> {
                    ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                        .finishAffinity()
                        .startFrom(this)
                }


            }
        }, SPLASH_DISPLAY_LENGTH)


    }
}