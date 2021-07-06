package com.example.adebuser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adebuser.R

class ForgotPasswordActivity : AppCompatActivity() {



    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }
}