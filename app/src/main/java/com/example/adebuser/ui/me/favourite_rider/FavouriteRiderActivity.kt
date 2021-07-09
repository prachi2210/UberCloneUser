package com.example.adebuser.ui.me.favourite_rider

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import com.example.adebuser.base.BaseActivity
import com.example.adebuser.databinding.ActivityFavouriteRiderBinding
import com.example.adebuser.ui.book_ride.ride_details.RideDetailsFragment

class FavouriteRiderActivity : BaseActivity(), FavoriteRiderAdapter.BookDriver{
    private lateinit var binding: ActivityFavouriteRiderBinding
    private val TAG: String = FavouriteRiderActivity::class.java.simpleName
    private val favoriteRiderAdapter: FavoriteRiderAdapter by lazy {
        FavoriteRiderAdapter(this , this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, FavouriteRiderActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteRiderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTitle.text = "Favourite Rider"
        binding.rvFavouriteRider.adapter = favoriteRiderAdapter


    }



    override fun onBookClick(position: Int) {
         val intent =  Intent()
        intent.putExtra("RiderBooked", "yes")
        setResult(RESULT_OK, intent)
        finish()

    }
}