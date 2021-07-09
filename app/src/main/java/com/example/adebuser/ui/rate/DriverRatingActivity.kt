package com.example.adebuser.ui.rate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.databinding.ActivityDriverRatingBinding
import com.example.adebuser.databinding.ActivityLoginBinding
import com.example.adebuser.ui.auth.LoginActivity

class DriverRatingActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDriverRatingBinding
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverRatingActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onClick(v: View?) {

    }
}