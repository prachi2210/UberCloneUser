package com.example.adebuser.ui.my_booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.databinding.FragmentMyBookingBinding
import com.example.adebuser.databinding.FragmentProfileBinding
import com.example.adebuser.ui.me.ProfileFragment
import com.example.adebuser.ui.me.favourite_rider.FavoriteRiderAdapter
import com.wizebrains.adventmingle.base.BaseFragment

class MyBookingFragment : BaseFragment() {

    private var _binding: FragmentMyBookingBinding? = null
    private val binding get() = _binding!!

    private val myBookingAdapter: MyBookingAdapter by lazy {
        MyBookingAdapter(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMyBooking.adapter = myBookingAdapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance(): MyBookingFragment = MyBookingFragment()
    }
}