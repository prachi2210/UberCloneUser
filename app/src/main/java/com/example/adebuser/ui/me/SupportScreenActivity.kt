package com.example.adebuser.ui.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.adebuser.databinding.ActivitySupportScreenBinding
import com.example.adebuser.extensions.hide
import com.example.adebuser.extensions.show
import com.example.adebuser.base.BaseActivity

class SupportScreenActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySupportScreenBinding

    var count=0

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SupportScreenActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.includeActionBar.tvTitle.text = "Support"
    }
    override fun onClick(v: View?) {

        when (v) {
            binding.includeActionBar.ivBack -> {
                onBackPressed()
            }

            binding.tvAccount -> {

                if (count == 0) {
                    count = 1
                    binding.tvEnquiry.show()
                    binding.btnCall.show()
                    binding.btnEmail.show()
                } else {
                    count = 0
                    binding.tvEnquiry.hide()
                    binding.btnCall.hide()
                    binding.btnEmail.hide()
                }
            }
            binding.ivAccountArrow ->
            {
                binding.tvAccount.performClick()
            }

        }
    }


}