package com.example.adebuser.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentProfileBinding
import com.example.adebuser.ui.me.favourite_rider.FavouriteRiderActivity
import com.example.adebuser.utils.ActivityStarter
import com.wizebrains.adventmingle.base.BaseFragment


class ProfileFragment : BaseFragment(), View.OnClickListener {
    private val binding get() = _binding!!
    private var _binding: FragmentProfileBinding? = null


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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvEditProfile.setOnClickListener(this)
        binding.tvSupport.setOnClickListener(this)
        binding.tvFavDriver.setOnClickListener(this)
        binding.tvSettings.setOnClickListener(this)

        binding.ivEditArrow.setOnClickListener(this)
        binding.ivArrowSupport.setOnClickListener(this)
        binding.ivArrowFav.setOnClickListener(this)
        binding.ivArrowSettings.setOnClickListener(this)
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
            binding.tvSettings->
            {
                ActivityStarter.of(SettingScreen.getStartIntent(requireActivity()))
                    .startFrom(this)
            }

            binding.ivArrowSettings->
            {
                binding.tvSettings.performClick()
            }


        }
    }
}