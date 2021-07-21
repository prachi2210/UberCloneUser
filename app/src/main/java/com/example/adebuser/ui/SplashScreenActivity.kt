package com.example.adebuser.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.adebuser.R
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.utils.ActivityStarter
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.ui.auth.LoginActivity
import com.example.adebuser.utils.Constants

class SplashScreenActivity : BaseActivity() {
    private val TAG: String = SplashScreenActivity::class.java.simpleName

    private var type: String? = null
    private var driverRef: String? = null
    private var driverPhoto: String? = null
    private var driverName: String? = null

    companion object {
        var SPLASH_DISPLAY_LENGTH: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        Log.e("PRACHI", bundle.toString())
        if (bundle != null) {
            type = bundle.getString(Constants.TYPE)
            driverPhoto = bundle.getString("driverProfilePic")
            driverName = bundle.getString("driverName")
            driverRef = bundle.getString("driverRef")
            Log.e("PRACHI", type.toString())
        }
        if (type.isNullOrEmpty()) {
            moveToDestination()
        } else {
            val intent = Intent(this@SplashScreenActivity, HomeScreenActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("driverName", driverName)
            intent.putExtra("driverPhoto", driverPhoto)
            intent.putExtra("driverRef", driverRef)
            startActivity(intent)
        }
    }

    private fun moveToDestination() {
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                userPreferences.getUserId().equals("") -> {
                    ActivityStarter.of(LoginActivity.getStartIntent(this))
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val bundle = intent!!.extras
        Log.e("PRACHI INTENT", bundle.toString())
        if (bundle != null) {
            type = bundle.getString(Constants.TYPE)
            driverPhoto = bundle.getString("driverProfilePic")
            driverName = bundle.getString("driverName")
            driverRef = bundle.getString("driverRef")
            Log.e("PRACHI INTENT", type.toString())
        }
        if (type.isNullOrEmpty()) {
            moveToDestination()
        } else {
            val intent1 = Intent(this@SplashScreenActivity, HomeScreenActivity::class.java)
            intent1.putExtra("type", type)
            intent1.putExtra("driverName", driverName)
            intent1.putExtra("driverPhoto", driverPhoto)
            intent1.putExtra("driverRef", driverRef)
            startActivity(intent1)
        }

    }
}