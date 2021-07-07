package com.example.adebuser.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.databinding.ActivitySignUpBinding
import com.example.adebuser.utils.ActivityStarter
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.utils.Constants
import com.example.adebuser.utils.Status

class SignUpActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: AuthViewModel
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
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.tvLogin -> {
                ActivityStarter.of(LoginActivity.getStartIntent(this))
                    .startFrom(this)
            }

            binding.tvRegister -> {
                signUp()
            }


        }
    }


    private fun signUp() {
        when {
            checkEmpty(binding.etName) -> {
                setError(getString(R.string.name_error))
            }

            checkEmpty(binding.etMobile) -> {
                setError(getString(R.string.mobile_error))
            }

            checkEmpty(binding.etEmail) -> {
                setError(getString(R.string.email_empty_error))
            }

            !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString().trim())
                .matches() -> {
                setError(getString(R.string.invalid_email_error))
            }

            checkEmpty(binding.etPassword) -> {
                setError(getString(R.string.password_error))
            }

            !binding.cb.isChecked -> {
                setError(getString(R.string.privacy_policy_validation))
            }

            else -> {
                viewModel.signUp(
                    binding.etName.text.toString().trim(),
                    binding.etEmail.text.toString().trim(),
                    binding.etMobile.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                    Constants.DEVICE_TOKEN
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        userPreferences.saveUserID(user.body()?.data?.id)
                                        userPreferences.saveUserRef(user.body()?.data?.userRef)
                                        userPreferences.saveName(user.body()?.data?.name)
                                        userPreferences.savePhoneNumber(user.body()?.data?.phoneNumber)
                                        ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                                            .finishAffinity()
                                            .startFrom(this)

                                    } else {
                                        setError(user.body()?.msg.toString())
                                    }
                                }
                            }
                            Status.ERROR -> {
                                dismissDialog()
                                setError(it.message.toString())

                            }
                            Status.LOADING -> {
                                showDialog()
                            }
                        }
                    }
                })
            }
        }

    }

}