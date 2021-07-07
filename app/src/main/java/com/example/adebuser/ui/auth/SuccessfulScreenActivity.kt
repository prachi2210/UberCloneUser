package com.example.adebuser.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.adebuser.databinding.ActivitySuccessfulScreenBinding
import com.example.adebuser.utils.ActivityStarter
import com.example.adebuser.base.BaseActivity

class SuccessfulScreenActivity : BaseActivity(), View.OnClickListener {


    private lateinit var binding: ActivitySuccessfulScreenBinding


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SuccessfulScreenActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfulScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {
                ActivityStarter.of(LoginActivity.getStartIntent(this))
                    .startFrom(this)
            }


        }
    }
}