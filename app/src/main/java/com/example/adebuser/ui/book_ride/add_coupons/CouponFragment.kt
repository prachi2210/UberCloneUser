package com.example.adebuser.ui.book_ride.add_coupons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.databinding.FragmentCouponBinding


class CouponFragment : Fragment() {

    private var _binding: FragmentCouponBinding? = null
    private val binding get() = _binding!!

    private val couponAdapter: CouponAdapter by lazy {
        CouponAdapter(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCouponBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.tvCloseFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@CouponFragment)
                .commit()
        }

        binding.dismissBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@CouponFragment)
                .commit()
        }

        binding.rvCoupons.adapter = couponAdapter

    }
}