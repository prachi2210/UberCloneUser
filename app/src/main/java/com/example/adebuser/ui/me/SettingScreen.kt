package com.example.adebuser.ui.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.adebuser.databinding.ActivitySettingScreenBinding
import com.example.adebuser.base.BaseActivity

class SettingScreen : BaseActivity() , View.OnClickListener{
    private lateinit var binding: ActivitySettingScreenBinding


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SettingScreen::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.includeActionBar.tvTitle.text = "Settings"
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.includeActionBar.ivBack -> {
                onBackPressed()
            }




        }
    }
}