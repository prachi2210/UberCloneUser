package com.example.adebuser.ui.payment_method

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.databinding.FragmentMyBookingBinding
import com.example.adebuser.databinding.FragmentPaymentBinding
import com.example.adebuser.ui.my_booking.MyBookingFragment
import com.wizebrains.adventmingle.base.BaseFragment

class PaymentFragment : BaseFragment() {


    private var _binding: FragmentPaymentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





    companion object {
        fun newInstance(): PaymentFragment = PaymentFragment()
    }



}