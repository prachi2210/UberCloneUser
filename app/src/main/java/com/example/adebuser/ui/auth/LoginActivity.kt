package com.example.adebuser.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.databinding.ActivityLoginBinding
import com.example.adebuser.ui.auth.forgotpassword.ForgotPasswordActivity
import com.example.adebuser.utils.ActivityStarter
import com.example.adebuser.utils.Constants
import com.example.adebuser.utils.Status
import com.example.adebuser.base.BaseActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: AuthViewModel

    var token = ""

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        generateFcmToken()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }

/*    private fun locationPermission() {


        Log.e(TAG, "Lattiude locationPermission" + userPreferences.getLatitude())
        Log.e(TAG, "longitude locationPermission" + userPreferences.getLongitude())
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        object : LocationListener {

            override fun onLocationChanged(location: Location) {
                userPreferences.saveCurrentLatitude(location.latitude.toString())
                userPreferences.saveCurrentLongitude(location.longitude.toString())

                Log.e(TAG, "getLatitude " + userPreferences.getLatitude())
                Log.e(TAG, "getLongitude " + userPreferences.getLongitude())

                Log.e(TAG, "IN ON LOCATION CHANGE, lat=" + location.latitude + ", lon=" + location.longitude);
                Log.e(TAG, "Lattiude Inside onLocation Changed" + userPreferences.getLatitude())
                Log.e(TAG, "longitude Inside onLocation Changed" + userPreferences.getLongitude())

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }.also { locationListener = it }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )


            Log.e(TAG, "Lattiude " + userPreferences.getLatitude())
            Log.e(TAG, "longitude " + userPreferences.getLongitude())
        } else {

            locationManager.run {
                requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000L, 1f,
                    locationListener)
                requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, locationListener);
            }

            Log.e(TAG, "Lattiude " + userPreferences.getLatitude())
            Log.e(TAG, "longitude " + userPreferences.getLongitude())
        }

    }*/


    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {

                login()
            }

            binding.tvSignup -> {
                ActivityStarter.of(SignUpActivity.getStartIntent(this))
                    .startFrom(this)
            }

            binding.tvForgotPassword -> {
                ActivityStarter.of(ForgotPasswordActivity.getStartIntent(this))
                    .startFrom(this)
            }

        }
    }

    private fun login() {

        when {
            checkEmpty(binding.etMobile) -> {
                setError(getString(R.string.mobile_error))
            }

            checkEmpty(binding.etPassword) -> {
                setError(getString(R.string.password_error))
            }

            else -> {
                viewModel.login(
                    binding.etMobile.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                  token
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        userPreferences.saveUserID(user.body()?.UserInfo?.id)
                                        userPreferences.saveUserRef(user.body()?.UserInfo?.userRef)
                                        userPreferences.saveName(user.body()?.UserInfo?.name)
                                        userPreferences.savePhoto(user.body()?.UserInfo?.profilePic)
                                        userPreferences.savePhoneNumber(user.body()?.UserInfo?.phoneNumber)
                                        ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                                            .finishAffinity()
                                            .startFrom(this)

                                    } else {
                                        setError(user.body()?.msg.toString())
                                    }
                                }
                            }
                            Status.ERROR -> {
                                dismissDialog()
                                setError(it.message.toString())

                            }
                            Status.LOADING -> {
                                showDialog()
                            }
                        }
                    }
                })
            }
        }

    }

    private fun generateFcmToken()  {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
           token = task.result
            Log.e("PRACHI", token)

        })

    }
}