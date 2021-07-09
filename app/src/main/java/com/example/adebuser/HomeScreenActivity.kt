package com.example.adebuser


import android.R.attr
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.databinding.ActivityHomeScreenBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.select_time.SelectTimeFragment
import com.example.adebuser.ui.me.ProfileFragment
import com.example.adebuser.ui.my_booking.MyBookingFragment
import com.example.adebuser.ui.payment_method.PaymentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeScreenActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private val bookFragment by lazy { BookRideFragment() }
    private val myBookingFragment by lazy { MyBookingFragment.newInstance() }
    private val paymentFragment by lazy { PaymentFragment.newInstance("navigation") }
    private val profileFragment by lazy { ProfileFragment.newInstance() }
    private val TAG = HomeScreenActivity::class.java.simpleName


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HomeScreenActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
        openFragment(bookFragment)

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


    private fun openFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment)
            commit()
        }


//    override fun onBackPressed() {
//        val id: Int = binding.bottomNavView.selectedItemId
//
//        Log.e(TAG, "bottom_navigation_id $id")
//        when {
//            bookFragment.isVisible -> {
//                binding.bottomNavView.selectedItemId = id
//            }
//            myBookingFragment.isVisible -> {
//                binding.bottomNavView.selectedItemId = id
//            }
//            paymentFragment.isVisible -> {
//                binding.bottomNavView.selectedItemId = id
//            }
//            profileFragment.isVisible -> {
//                binding.bottomNavView.selectedItemId = id
//            }
//
//            else -> super.onBackPressed()
//        }


  //  }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val text: String = data?.getStringExtra("RiderBooked")!!

            Log.e("PRACHI", text)

            if (text == "yes") {
                openFragment(BookRideFragment.newInstance("booked"))
            }
        }

    }


}