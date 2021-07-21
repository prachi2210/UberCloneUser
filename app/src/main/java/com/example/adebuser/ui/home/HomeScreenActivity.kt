package com.example.adebuser.ui.home


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.adebuser.R
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.base.MyApplication
import com.example.adebuser.databinding.ActivityHomeScreenBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.LocationViewModel
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.example.adebuser.ui.book_ride.select_time.SelectTimeFragment
import com.example.adebuser.ui.me.ProfileFragment
import com.example.adebuser.ui.my_booking.MyBookingFragment
import com.example.adebuser.ui.payment_method.PaymentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.example.adebuser.ui.book_ride.select_time.SelectTimeHourlyFragment
import com.google.android.gms.maps.model.LatLng


class HomeScreenActivity : BaseActivity(), BookRideFragment.OnMapClickListener {

    private lateinit var binding: ActivityHomeScreenBinding
    private val bookFragment by lazy { BookRideFragment() }
    private val myBookingFragment by lazy { MyBookingFragment.newInstance() }
    private val paymentFragment by lazy { PaymentFragment.newInstance("navigation") }
    private val profileFragment by lazy { ProfileFragment.newInstance() }
    private lateinit var viewModel: LocationViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HomeScreenActivity::class.java)
        }
    }
    private val mNotificationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            Log.e("PRACHI Broadcast", intent.getStringExtra("type").toString())

            val type = intent.getStringExtra("type")
            val driverRef = intent.getStringExtra("driverRef")
            val driverPhoto = intent.getStringExtra("driverPhoto")
            val driverName = intent.getStringExtra("driverName")
            if (!type.isNullOrEmpty()) {
                Log.e("PRACHI", type.toString())
                openFragment(BookRideFragment.newInstanceForRating("booked", type, driverRef, driverName, driverPhoto))
            }
        }
    }

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = this.run {
            ViewModelProvider(this).get(LocationViewModel::class.java)
        }

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
        openFragment(bookFragment)

        val type = intent?.getStringExtra("type")
        val driverRef = intent?.getStringExtra("driverRef")
        val driverPhoto = intent?.getStringExtra("driverPhoto")
        val driverName = intent?.getStringExtra("driverName")
        if (!type.isNullOrEmpty()) {
            Log.e("PRACHI", type.toString())
            openFragment(BookRideFragment.newInstanceForRating("booked", type, driverRef, driverName, driverPhoto))
        }
    }

    override fun onStart() {
        super.onStart()
        MyApplication.active = true

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mNotificationReceiver,
            IntentFilter("Message")
        )

    }

    override fun onStop() {
        super.onStop()
        MyApplication.active = false
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mNotificationReceiver);
    }


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_book -> {
                    openFragment(bookFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_my_booking -> {
                    openFragment(myBookingFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_payment_method -> {
                    openFragment(paymentFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    openFragment(profileFragment)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }


    private fun openFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.frame_container, fragment)
        commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val text: String = data?.getStringExtra("RiderBooked")!!



            if (text == "yes") {
                openFragment(BookRideFragment.newInstance("booked", "searching driver"))

            }
        }

    }

    override fun onMapClick(
        latLngPickUp: LatLng,
        latLngDropOff: LatLng,
        type: String?,
        pickUpName: String,
        dropOffName: String
    ) {

        val bookRideRequest = BookRideRequest()

        val fragment: CarTypeFragment? =
            supportFragmentManager.findFragmentByTag("car") as CarTypeFragment?
        val fragment2: SelectTimeFragment? =
            supportFragmentManager.findFragmentByTag("time") as SelectTimeFragment?
        val fragment3: SelectTimeHourlyFragment? =
            supportFragmentManager.findFragmentByTag("time hour") as SelectTimeHourlyFragment?

        bookRideRequest.userRef = userPreferences.getUserREf()
        bookRideRequest.pickupLat = latLngPickUp.latitude.toString()
        bookRideRequest.pickupLong = latLngPickUp.longitude.toString()

        bookRideRequest.dropOffLat = latLngDropOff.latitude.toString()
        bookRideRequest.dropOffLong = latLngDropOff.longitude.toString()

        bookRideRequest.pickUpName = pickUpName
        bookRideRequest.dropOffName = dropOffName

        when {
            fragment != null && fragment.isVisible -> {
                viewModel.setLatLong(latLngDropOff)
            }
            fragment2 != null && fragment2.isVisible -> {
                openFragmentSmall(CarTypeFragment.newInstance(type!!, bookRideRequest), "car")

            }
            fragment3 != null && fragment3.isVisible -> {
                openFragmentSmall(CarTypeFragment.newInstance(type!!, bookRideRequest), "car")

            }
        }
    }


}