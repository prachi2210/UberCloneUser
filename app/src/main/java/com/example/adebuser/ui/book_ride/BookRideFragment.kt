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
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentBookRideBinding
import com.example.adebuser.ui.book_ride.ride_details.RideDetailsFragment
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.wizebrains.adventmingle.base.BaseFragment


class BookRideFragment : BaseFragment() {
    private var param: String? = null
    private var type: String? = null

    private var _binding: FragmentBookRideBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString("status")
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

        type = (activity as HomeScreenActivity).userPreferences.getTimeType()

        if (type.isNullOrEmpty())
        {
            type = "now"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
        }



        if (param == "booked") {
            binding.tvOpenFragment.text = "Driver is 15 min away from you"

        } else {
            binding.tvOpenFragment.text = getString(R.string.choose_your_car_and_gear_type)

        }


        binding.tvOpenFragment.setOnClickListener {
            if (param == "booked") {
                openFragmentSmall(RideDetailsFragment(), "ride")
            } else {
                openFragmentSmall(CarTypeFragment.newInstance(type!!), "car")
            }
        }

        setButtonsState()
    }


    override fun onResume() {
        super.onResume()
        setBackgroundAccordingToType()

    }

    private fun setButtonsState() {
        binding.btnNow.setOnClickListener {
            type = "now"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (param == "booked") {
                replaceFragmentSmall(RideDetailsFragment(), "ride")
            } else {
                replaceFragmentSmall(CarTypeFragment.newInstance(type!!), "car")
            }
        }


        binding.btnDay.setOnClickListener {
            type = "day"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (param == "booked") {
                replaceFragmentSmall(RideDetailsFragment(), "ride")
            } else {
                replaceFragmentSmall(CarTypeFragment.newInstance(type!!), "car")
            }

        }

        binding.btnHourly.setOnClickListener {
            type = "hourly"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (param == "booked") {
                replaceFragmentSmall(RideDetailsFragment(), "ride")
            } else {
                replaceFragmentSmall(CarTypeFragment.newInstance(type!!), "car")
            }

        }

        binding.btnRegular.setOnClickListener {
            type = "regular"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (param == "booked") {
                replaceFragmentSmall(RideDetailsFragment(), "ride")
            } else {
                replaceFragmentSmall(CarTypeFragment.newInstance(type!!), "car")
            }

        }
    }


    private fun setBackgroundAccordingToType() {
        when ((activity as HomeScreenActivity).userPreferences.getTimeType()) {
            "now" -> {
                buttonActiveState(binding.btnNow)
                buttonInActiveState(binding.btnDay)
                buttonInActiveState(binding.btnHourly)
                buttonInActiveState(binding.btnRegular)
            }

            "day" -> {
                buttonActiveState(binding.btnDay)
                buttonInActiveState(binding.btnNow)
                buttonInActiveState(binding.btnHourly)
                buttonInActiveState(binding.btnRegular)
            }

            "hourly" -> {
                (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
                buttonActiveState(binding.btnHourly)
                buttonInActiveState(binding.btnDay)
                buttonInActiveState(binding.btnNow)
                buttonInActiveState(binding.btnRegular)
            }

            "regular" -> {
                buttonActiveState(binding.btnRegular)
                buttonInActiveState(binding.btnDay)
                buttonInActiveState(binding.btnNow)
                buttonInActiveState(binding.btnHourly)
            }

        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param: String) =
            BookRideFragment().apply {
                arguments = Bundle().apply {
                    putString("status", param)

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