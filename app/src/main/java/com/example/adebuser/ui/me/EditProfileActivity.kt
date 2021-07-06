package com.example.adebuser.ui.me

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.adebuser.databinding.ActivityEditProfileBinding
import com.example.adebuser.databinding.ActivityHomeScreenBinding
import com.example.adebuser.utils.ActivityStarter
import com.wizebrains.adventmingle.base.BaseActivity

class EditProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, EditProfileActivity::class.java)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onClick(v: View?) {

        when (v) {
            binding.includeActionBar.ivBack -> {
            onBackPressed()
            }




        }
    }



}