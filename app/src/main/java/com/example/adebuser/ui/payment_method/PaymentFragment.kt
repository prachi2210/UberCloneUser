package com.example.adebuser.ui.payment_method

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.databinding.FragmentPaymentBinding
import com.example.adebuser.extensions.hide
import com.example.adebuser.extensions.show
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.wizebrains.adventmingle.base.BaseFragment

class PaymentFragment : BaseFragment() {

    private var type: String? = null
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val TAG = PaymentFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e(TAG,"type=$type")


        if (type == "home") {
            binding.ivBack.show()
            binding.ivLogo.hide()
        } else {
            binding.ivLogo.show()
            binding.ivBack.hide()

        }

        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@PaymentFragment).commit()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    companion object {

        @JvmStatic
        fun newInstance(param: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString("type", param)

                }
            }
    }



}