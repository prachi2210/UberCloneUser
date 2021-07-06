package com.example.adebuser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.databinding.ActivityLoginBinding
import com.example.adebuser.ui.me.EditProfileActivity
import com.example.adebuser.utils.ActivityStarter
import com.wizebrains.adventmingle.base.BaseActivity

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {
                ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                    .startFrom(this)
            }

            binding.tvSignup -> {
                ActivityStarter.of(SignUpActivity.getStartIntent(this))
                    .startFrom(this)
            }



        }
    }
}