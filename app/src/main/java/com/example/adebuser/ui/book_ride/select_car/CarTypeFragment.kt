package com.example.adebuser.ui.book_ride.select_car

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentCarTypeBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.add_coupons.CouponFragment

import com.example.adebuser.ui.book_ride.select_time.SelectTimeFragment
import com.example.adebuser.ui.book_ride.select_time.SelectTimeHourlyFragment
import com.example.adebuser.ui.me.favourite_rider.FavouriteDriverActivity
import com.example.adebuser.ui.payment_method.PaymentFragment
import com.example.adebuser.base.BaseFragment


class CarTypeFragment : BaseFragment() {


    private var _binding: FragmentCarTypeBinding? = null
    private val binding get() = _binding!!
    private var type: String? = null
    private var carType: String? = "small"
    private var gearType: String? = "manual"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCarTypeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCloseFragment.setOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                .commit()
        }

        binding.paymentMethod.setOnClickListener {

            addSlidingFragment(
                requireActivity().supportFragmentManager,
                PaymentFragment.newInstance("home"), "payment",
                R.id.frame_container
            )
        }

        binding.tvCoupons.setOnClickListener {
            addSlidingFragment(
                requireActivity().supportFragmentManager,
                CouponFragment(), "coupon",
                R.id.flContainerSlide
            )
        }



        binding.linearSmall.setOnClickListener{
            carType = "small"
            binding.ivSmallCar.alpha=1f
            binding.ivMediumCar.alpha=0.3f
            binding.ivLargeCar.alpha=0.3f
            binding.ivLuxoryCar.alpha=0.3f

        }

        binding.linearMedium.setOnClickListener{
            carType = "medium"
            binding.ivMediumCar.alpha=1f
            binding.ivSmallCar.alpha=0.3f
            binding.ivLargeCar.alpha=0.3f
            binding.ivLuxoryCar.alpha=0.3f
        }



        binding.linearLarge.setOnClickListener{
            carType = "large"
            binding.ivLargeCar.alpha=1f
            binding.ivSmallCar.alpha=0.3f
            binding.ivMediumCar.alpha=0.3f
            binding.ivLuxoryCar.alpha=0.3f
        }

        binding.linearLuxory.setOnClickListener{
            carType = "luxury"
            binding.ivLuxoryCar.alpha=1f
            binding.ivSmallCar.alpha=0.3f
            binding.ivMediumCar.alpha=0.3f
            binding.ivLargeCar.alpha=0.3f

        }



        binding.btnGearTypeAutomatic.setOnClickListener {
            gearType = "manual"
            buttonActiveState(binding.btnGearTypeAutomatic)
            buttonInActiveState(binding.btnGearTypeManual)
        }

        binding.btnGearTypeManual.setOnClickListener {
            gearType = "automatic"
            buttonInActiveState(binding.btnGearTypeAutomatic)
            buttonActiveState(binding.btnGearTypeManual)
        }



        binding.bookRide.setOnClickListener {

            when (type) {
                "now" -> {
                    openDialog()
                }
                "day" -> {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                        .commit()
                    openFragmentSmall(SelectTimeFragment.newInstance(type!!), "time")

                }

                "regular" -> {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                        .commit()
                    openFragmentSmall(SelectTimeFragment.newInstance(type!!), "time")
                }

                "hourly" -> {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                        .commit()
                    openFragmentSmall(SelectTimeHourlyFragment.newInstance(type!!), "time")

                }
            }


        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            CarTypeFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                }
            }
    }


    private fun openDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.driver_selection_layout)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show()

        val cancelBtn: ImageView = dialog.findViewById(R.id.cancel)
        val favoriteBtn: LinearLayoutCompat = dialog.findViewById(R.id.select_favorite_driver)
        val automaticBtn: LinearLayoutCompat = dialog.findViewById(R.id.choose_automatically)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        favoriteBtn.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), FavouriteDriverActivity::class.java)
            startActivityForResult(intent, 1);
            dialog.dismiss()


        }

        automaticBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                .commit()

            replaceFragmentFull(BookRideFragment.newInstance("booked"), "booked ride")
            dialog.dismiss()


        }

    }

    private fun buttonActiveState(appCompatButton: AppCompatButton) {
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        appCompatButton.setBackgroundResource(
            R.drawable.drawable_blue_curve
        )

    }


    private fun buttonInActiveState(appCompatButton: AppCompatButton) {
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        appCompatButton.setBackgroundResource(
            R.drawable.drawable_editext

        )

    }

}