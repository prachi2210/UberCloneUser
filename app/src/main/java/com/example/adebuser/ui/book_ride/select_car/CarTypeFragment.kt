package com.example.adebuser.ui.book_ride.select_car

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentCarTypeBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.add_coupons.CouponFragment
import com.example.adebuser.ui.book_ride.ride_details.RideDetailsFragment
import com.example.adebuser.ui.book_ride.select_time.SelectTimeFragment
import com.example.adebuser.ui.me.favourite_rider.FavouriteRiderActivity
import com.example.adebuser.ui.payment_method.PaymentFragment
import com.wizebrains.adventmingle.base.BaseFragment


class CarTypeFragment : BaseFragment() {


    private var _binding: FragmentCarTypeBinding? = null
    private val binding get() = _binding!!
    private var type: String? = null


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
            val intent: Intent = Intent(requireActivity(), FavouriteRiderActivity::class.java)
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

}