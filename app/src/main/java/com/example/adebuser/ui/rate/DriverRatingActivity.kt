package com.example.adebuser.ui.rate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.adebuser.R
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.databinding.ActivityDriverRatingBinding
import com.example.adebuser.databinding.ActivityLoginBinding
import com.example.adebuser.ui.auth.LoginActivity
import com.example.adebuser.utils.Constants

class DriverRatingActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDriverRatingBinding

    private var driverRef: String? = null
    private var driverPhoto: String? = null
    private var driverName: String? = null

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverRatingActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val driverRef = intent?.getStringExtra("driverRef")
        val driverPhoto = intent?.getStringExtra("driverPhoto")
        val driverName = intent?.getStringExtra("driverName")


        binding.tvDriverName.text = driverName
        Glide.with(this).load(Constants.PHOTO_BASE_URL + driverPhoto).apply(
            RequestOptions(
            ).placeholder(R.drawable.profile)
        ).into(binding.ivProfile)


        binding.btnRate.setOnClickListener {
            showDialog()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
                dismissDialog()
            }, 4000)
        }
    }


    override fun onClick(v: View?) {

    }
}