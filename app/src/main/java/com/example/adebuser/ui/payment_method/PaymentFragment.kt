package com.example.adebuser.ui.payment_method

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.databinding.FragmentPaymentBinding
import com.example.adebuser.base.BaseFragment

class PaymentFragment : BaseFragment() {

    private var type: String? = null
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!


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

        if (type == "home") {
            binding.backPress.visibility = View.VISIBLE
        } else {
            binding.backPress.visibility = View.GONE

        }

        binding.backPress.setOnClickListener {
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