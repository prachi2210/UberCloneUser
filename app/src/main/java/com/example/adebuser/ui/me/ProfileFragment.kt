package com.example.adebuser.ui.me

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.databinding.FragmentProfileBinding
import com.example.adebuser.ui.auth.AuthViewModel
import com.example.adebuser.ui.auth.LoginActivity
import com.example.adebuser.ui.me.favourite_rider.FavouriteRiderActivity
import com.example.adebuser.ui.rate.DriverRatingActivity
import com.example.adebuser.utils.ActivityStarter
import com.example.adebuser.utils.Status
import com.wizebrains.adventmingle.base.BaseFragment


class ProfileFragment : BaseFragment(), View.OnClickListener {
    private val binding get() = _binding!!
    private var _binding: FragmentProfileBinding? = null
    private lateinit var viewModel: AuthViewModel

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUsername.text=(activity as HomeScreenActivity).userPreferences.getName()
        binding.tvPhoneNumber.text=(activity as HomeScreenActivity).userPreferences.getPhoneNumber()
        setupViewModel()
        binding.tvEditProfile.setOnClickListener(this)
        binding.tvSupport.setOnClickListener(this)
        binding.tvFavDriver.setOnClickListener(this)
        binding.tvSettings.setOnClickListener(this)
        binding.ivEditArrow.setOnClickListener(this)
        binding.ivArrowSupport.setOnClickListener(this)
        binding.ivArrowFav.setOnClickListener(this)
        binding.ivArrowSettings.setOnClickListener(this)
        binding.ivRightPlay.setOnClickListener(this)
        binding.tvRate.setOnClickListener(this)
     /*   binding.tvRate.setOnClickListener(this)*/
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun onClick(v: View?) {

        when (v) {
            binding.tvEditProfile -> {
                ActivityStarter.of(EditProfileActivity.getStartIntent(requireActivity()))
                    .startFrom(this)
            }

            binding.ivRightPlay -> {
                logoutDialog()
            }


            binding.ivEditArrow -> {
                binding.tvEditProfile.performClick()
            }

            binding.tvSupport -> {
                ActivityStarter.of(SupportScreenActivity.getStartIntent(requireActivity()))
                    .startFrom(this)
            }
            binding.ivArrowSupport -> {
                binding.tvSupport.performClick()
            }

            binding.tvFavDriver -> {
                ActivityStarter.of(FavouriteRiderActivity.getStartIntent(requireActivity()))
                    .startFrom(this)
            }

            binding.ivArrowFav -> {
                ActivityStarter.of(FavouriteRiderActivity.getStartIntent(requireActivity()))
                    .startFrom(this)
            }
            binding.tvSettings -> {
                ActivityStarter.of(SettingScreen.getStartIntent(requireActivity()))
                    .startFrom(this)
            }

            binding.ivArrowSettings -> {
                binding.tvSettings.performClick()
            }

            binding.tvRate -> {
                ActivityStarter.of(DriverRatingActivity.getStartIntent(requireActivity()))
                    .startFrom(this)
            }
        }
    }

    private fun logoutDialog()
    {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.logout))
            .setMessage(R.string.logout_confirm_message)
            .setPositiveButton("Confirm") { _, _ -> logout() }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    private fun logout() {

        viewModel.logout(
            (activity as HomeScreenActivity).userPreferences.getUserREf().trim(),
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as HomeScreenActivity).dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                ActivityStarter
                                    .of(LoginActivity.getStartIntent(requireActivity()))
                                    .finishAffinity()
                                    .startFrom(requireActivity())
                                (activity as HomeScreenActivity).userPreferences.clearPrefs()

                            } else {
                                (activity as HomeScreenActivity).setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    Status.ERROR -> {
                        (activity as HomeScreenActivity).dismissDialog()
                        (activity as HomeScreenActivity).setError(it.message.toString())

                    }
                    Status.LOADING -> {
                        // showDialog()
                    }
                }
            }
        })
    }
}