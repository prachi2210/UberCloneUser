package com.example.adebuser.ui.me.favourite_rider

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.R

import com.example.adebuser.base.BaseActivity
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.databinding.ActivityFavouriteRiderBinding
import com.example.adebuser.ui.book_ride.BookRideViewModel
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.ui.me.favourite_rider.response.FavoriteDriver
import com.example.adebuser.utils.Status.*
import kotlin.collections.ArrayList

class FavouriteDriverActivity : BaseActivity(), FavoriteDriverAdapter.BookDriver {

    private lateinit var viewModel: FavoriteDriverViewModel
    private lateinit var bookViewModel: BookRideViewModel
    private var bookRideRequest: BookRideRequest? = null
    private var from: String? = null


    private lateinit var binding: ActivityFavouriteRiderBinding

    private val favoriteRiderList: ArrayList<FavoriteDriver> = ArrayList()


    private val favoriteRiderAdapter: FavoriteDriverAdapter by lazy {
        FavoriteDriverAdapter(this, this, favoriteRiderList , from)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, FavouriteDriverActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteRiderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTitle.text = getString(R.string.favorite_rider)

        bookRideRequest = intent.getParcelableExtra("data")

        from = intent.getStringExtra("from")

        binding.rvFavouriteRider.adapter = favoriteRiderAdapter


        setupViewModel()

    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(FavoriteDriverViewModel::class.java)

        bookViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(BookRideViewModel::class.java)
    }

    override fun onBookClick(position: Int, userRef: String) {

        bookRideRequest?.favoriteDriverRef = userRef

        bookViewModel.bookRide(bookRideRequest!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                val intent = Intent()
                                intent.putExtra("RiderBooked", "yes")
                                setResult(RESULT_OK, intent)
                                finish()
                            } else {
                                setError(user.body()?.msg.toString())

                            }

                        }

                    }
                    ERROR -> {
                        dismissDialog()
                        setError(it.message.toString())
                    }
                    LOADING -> {
                        showDialog()
                    }
                }
            }
        })


    }


    override fun onResume() {
        super.onResume()

        viewModel.getFavoriteDrivers(
            userPreferences.getUserREf(),
            bookRideRequest?.pickupLat.toString(),
            bookRideRequest?.pickupLong.toString(),
            bookRideRequest?.gearType.toString(),
            bookRideRequest?.carType.toString()
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                favoriteRiderList.addAll(user.body()!!.favoriteDriverList)
                                favoriteRiderAdapter.notifyDataSetChanged()

                            } else {
                                setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    ERROR -> {
                        dismissDialog()
                        setError(it.message.toString())

                    }
                    LOADING -> {
                        showDialog()
                    }
                }
            }
        })
    }
}
