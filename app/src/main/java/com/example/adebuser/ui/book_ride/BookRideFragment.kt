 package com.example.adebuser.ui.book_ride

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentBookRideBinding
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.wizebrains.adventmingle.base.BaseFragment


class BookRideFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var type: String? = null

    private var _binding: FragmentBookRideBinding? = null
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
        _binding = FragmentBookRideBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvOpenFragment.setOnClickListener {
            openFragment(CarTypeFragment(), "car")
        }


        setButtonsState()
    }

    private fun setButtonsState() {
        binding.btnNow.setOnClickListener {
            type = "now"
            buttonActiveState(binding.btnNow)
            buttonInActiveState(binding.btnDay)
            buttonInActiveState(binding.btnHourly)
            buttonInActiveState(binding.btnRegular)
        }


        binding.btnDay.setOnClickListener {
            type = "day"
            buttonActiveState(binding.btnDay)
            buttonInActiveState(binding.btnNow)
            buttonInActiveState(binding.btnHourly)
            buttonInActiveState(binding.btnRegular)
        }

        binding.btnHourly.setOnClickListener {
            type = "hourly"
            buttonActiveState(binding.btnHourly)
            buttonInActiveState(binding.btnDay)
            buttonInActiveState(binding.btnNow)
            buttonInActiveState(binding.btnRegular)
        }

        binding.btnRegular.setOnClickListener {
            type = "regular"
            buttonActiveState(binding.btnRegular)
            buttonInActiveState(binding.btnDay)
            buttonInActiveState(binding.btnNow)
            buttonInActiveState(binding.btnHourly)
        }
    }


    private fun openFragment(fragment: Fragment, tag: String) {
        replaceSlidingFragment(
            requireActivity().supportFragmentManager,
            fragment,
            tag,
            R.id.flContainerSlide
        )
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookRideFragment().apply {
                arguments = Bundle().apply {
                    putString("", param1)
                    putString("", param2)
                }
            }
    }

    private fun replaceSlidingFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String, @IdRes container: Int
    ) {
        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_up_dialog, 0, 0,
            R.anim.slide_down_dialog
        ).replace(container, fragment, tag).commit()
    }


    private fun buttonActiveState(appCompatButton: AppCompatButton) {
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        appCompatButton.setBackgroundResource(
            R.drawable.drawable_blue_curve
        )

    }


    private fun buttonInActiveState(appCompatButton: AppCompatButton) {
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        appCompatButton.setBackgroundColor(
            Color.TRANSPARENT

        )

    }


}