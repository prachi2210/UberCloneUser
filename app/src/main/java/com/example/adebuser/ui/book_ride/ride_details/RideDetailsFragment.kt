package com.example.adebuser.ui.book_ride.ride_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.adebuser.databinding.FragmentRideDetailsBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.base.BaseFragment


class RideDetailsFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentRideDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("")
            param2 = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRideDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@RideDetailsFragment)
                .commit()
        }

        binding.cancelRide.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            replaceFragmentFull(BookRideFragment.newInstance("choose vehicle"), "book")
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RideDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("", param1)
                    putString("", param2)
                }
            }
    }
}