package com.example.adebuser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.databinding.ActivityLoginBinding
import com.example.adebuser.databinding.ActivitySignUpBinding
import com.example.adebuser.utils.ActivityStarter
import com.wizebrains.adventmingle.base.BaseActivity

class SignUpActivity : BaseActivity(), View.OnClickListener {


    private lateinit var binding: ActivitySignUpBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


    override fun onClick(v: View?) {
        when (v) {
            binding.tvLogin -> {
                ActivityStarter.of(LoginActivity.getStartIntent(this))
                    .startFrom(this)
            }


        }
    }
}