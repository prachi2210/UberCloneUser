package com.example.adebuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.utils.ActivityStarter
import com.wizebrains.adventmingle.base.BaseActivity

class SplashScreenActivity : BaseActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val TAG: String = SplashScreenActivity::class.java.simpleName

    companion object {
        var SPLASH_DISPLAY_LENGTH: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveToDestination()


    }

    private fun moveToDestination() {
        handler = Handler()
        runnable = Runnable {
            ActivityStarter.of(LoginActivity.getStartIntent(this))
                .startFrom(this)


        }


        handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH)

    }
}