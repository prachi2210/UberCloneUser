package com.example.adebuser.ui.me.favourite_rider

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.adebuser.R
import com.example.adebuser.databinding.ActivityFavouriteRiderBinding
import com.example.adebuser.databinding.ActivitySupportScreenBinding
import com.wizebrains.adventmingle.base.BaseActivity

class FavouriteRiderActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityFavouriteRiderBinding
    private val TAG: String = FavouriteRiderActivity::class.java.simpleName
    private val favoriteRiderAdapter: FavoriteRiderAdapter by lazy {
        FavoriteRiderAdapter(this)
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
        binding.includeActionBar.tvTitle.text="Favourite Rider"
        binding.rvFavouriteRider.adapter=favoriteRiderAdapter


    }


    override fun onClick(v: View?) {

        when (v) {
            binding.includeActionBar.ivBack -> {
                onBackPressed()
            }




        }
    }
}