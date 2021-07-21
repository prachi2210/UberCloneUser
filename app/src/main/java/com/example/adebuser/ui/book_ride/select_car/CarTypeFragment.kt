package com.example.adebuser.ui.book_ride.select_car

import android.app.Dialog
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.R
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.databinding.FragmentCarTypeBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.BookRideViewModel
import com.example.adebuser.ui.book_ride.LocationViewModel
import com.example.adebuser.ui.book_ride.add_coupons.CouponFragment
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.ui.book_ride.select_time.SelectTimeFragment
import com.example.adebuser.ui.book_ride.select_time.SelectTimeHourlyFragment
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.ui.me.favourite_rider.FavouriteDriverActivity
import com.example.adebuser.ui.payment_method.PaymentFragment
import com.example.adebuser.utils.Status
import java.text.SimpleDateFormat
import java.util.*


class CarTypeFragment : BaseFragment() {


    private var _binding: FragmentCarTypeBinding? = null
    private val binding get() = _binding!!
    private var type: String? = null
    private var newLat: String? = null
    private var newLong: String? = null
    private var destinationName: String? = null
    private lateinit var bookRideRequest: BookRideRequest
    private var carType: String? = "Small"
    private var gearType: String? = "Manual"
    private lateinit var viewModel: LocationViewModel
    private lateinit var bookViewModel: BookRideViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")
            bookRideRequest = it.getParcelable<BookRideRequest>("requestData")!!
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

        bookViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(BookRideViewModel::class.java)

        viewModel = activity?.run {
            ViewModelProvider(this).get(LocationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        viewModel.latLong.observe(requireActivity(), androidx.lifecycle.Observer {


            val latLong = viewModel.latLong.value

            newLat = latLong?.latitude.toString()
            newLong = latLong?.longitude.toString()

            val geocoder = Geocoder(requireContext(), Locale.getDefault())

            val addresses: List<Address> = geocoder.getFromLocation(
                newLat!!.toDouble(),
                newLong!!.toDouble(),
                1
            )

            val address: String =
                addresses[0].getAddressLine(0)


            destinationName = address




        })
        val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

        binding.date.text = sdf.format(Date())

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



        binding.linearSmall.setOnClickListener {
            carType = "Small"
            binding.ivSmallCar.alpha = 1f
            binding.ivMediumCar.alpha = 0.3f
            binding.ivLargeCar.alpha = 0.3f
            binding.ivLuxoryCar.alpha = 0.3f

        }

        binding.linearMedium.setOnClickListener {
            carType = "Medium"
            binding.ivMediumCar.alpha = 1f
            binding.ivSmallCar.alpha = 0.3f
            binding.ivLargeCar.alpha = 0.3f
            binding.ivLuxoryCar.alpha = 0.3f
        }



        binding.linearLarge.setOnClickListener {
            carType = "Large"
            binding.ivLargeCar.alpha = 1f
            binding.ivSmallCar.alpha = 0.3f
            binding.ivMediumCar.alpha = 0.3f
            binding.ivLuxoryCar.alpha = 0.3f
        }

        binding.linearLuxory.setOnClickListener {
            carType = "Luxury"
            binding.ivLuxoryCar.alpha = 1f
            binding.ivSmallCar.alpha = 0.3f
            binding.ivMediumCar.alpha = 0.3f
            binding.ivLargeCar.alpha = 0.3f

        }



        binding.btnGearTypeAutomatic.setOnClickListener {
            gearType = "Manual"
            buttonActiveState(binding.btnGearTypeAutomatic)
            buttonInActiveState(binding.btnGearTypeManual)
        }

        binding.btnGearTypeManual.setOnClickListener {
            gearType = "AutoMatic"
            buttonInActiveState(binding.btnGearTypeAutomatic)
            buttonActiveState(binding.btnGearTypeManual)
        }



        binding.bookRide.setOnClickListener {

            if (!newLat.isNullOrEmpty() && !newLong.isNullOrEmpty() && !destinationName.isNullOrEmpty()) {
                bookRideRequest.dropOffLat = newLat
                bookRideRequest.dropOffLong = newLong
                bookRideRequest.dropOffName = destinationName
            }

            when {
                (bookRideRequest.dropOffLat.isNullOrEmpty()) -> {
                    (activity as HomeScreenActivity).setError(getString(R.string.destination_error))
                }

                else -> {
                    when (type) {
                        "now" -> {
                            bookRideRequest.carType = carType
                            bookRideRequest.chooseType = type
                            bookRideRequest.gearType = gearType
                            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                            val currentDate: String = sdf.format(Date())
                            bookRideRequest.scheduleTime = currentDate
                            bookRideRequest.paymentMode = "cash"
                            bookRideRequest.fareAmount = "40"

                            openDialog()
                        }
                        "day" -> {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .remove(this@CarTypeFragment)
                                .commit()
                            bookRideRequest.carType = carType
                            bookRideRequest.chooseType = type
                            bookRideRequest.gearType = gearType
                            bookRideRequest.paymentMode = "cash"
                            bookRideRequest.fareAmount = "50"
                            openFragmentSmall(
                                SelectTimeFragment.newInstance(type!!, bookRideRequest),
                                "time"
                            )

                        }

                        "regular" -> {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .remove(this@CarTypeFragment)
                                .commit()
                            bookRideRequest.carType = carType
                            bookRideRequest.chooseType = type
                            bookRideRequest.gearType = gearType
                            bookRideRequest.paymentMode = "cash"
                            bookRideRequest.fareAmount = "50"

                            openFragmentSmall(
                                SelectTimeFragment.newInstance(type!!, bookRideRequest),
                                "time"
                            )
                        }

                        "hourly" -> {
                            bookRideRequest.carType = carType
                            bookRideRequest.chooseType = type
                            bookRideRequest.gearType = gearType
                            bookRideRequest.paymentMode = "cash"
                            bookRideRequest.fareAmount = "50"
                            requireActivity().supportFragmentManager.beginTransaction()
                                .remove(this@CarTypeFragment)
                                .commit()
                            openFragmentSmall(
                                SelectTimeHourlyFragment.newInstance(bookRideRequest),
                                "time hour"
                            )

                        }
                    }
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String, bookRideRequest: BookRideRequest) =
            CarTypeFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                    putParcelable("requestData", bookRideRequest)
                }
            }
    }


    private fun openDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.driver_selection_layout)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val cancelBtn: ImageView = dialog.findViewById(R.id.cancel)
        val favoriteBtn: LinearLayoutCompat = dialog.findViewById(R.id.select_favorite_driver)
        val automaticBtn: LinearLayoutCompat = dialog.findViewById(R.id.choose_automatically)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        favoriteBtn.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), FavouriteDriverActivity::class.java)
            intent.putExtra("data", bookRideRequest)
            startActivityForResult(intent, 1)
            dialog.dismiss()


        }

        automaticBtn.setOnClickListener {

            bookViewModel.bookRide(bookRideRequest)
                .observe(requireActivity(), androidx.lifecycle.Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                (activity as HomeScreenActivity).dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .remove(this@CarTypeFragment)
                                            .commit()

                                        replaceFragmentFull(
                                            BookRideFragment.newInstance("booked","searching driver"),
                                            "booked"
                                        )
                                        dialog.dismiss()
                                    } else {
                                        (activity as HomeScreenActivity).setError(user.body()?.msg.toString())

                                    }
                                }

                            }
                            Status.ERROR -> {
                                (activity as HomeScreenActivity).dismissDialog()
                                (activity as HomeScreenActivity).setError(it.message.toString())
                            }
                            Status.LOADING -> {
                                (activity as HomeScreenActivity).showDialog()
                            }
                        }
                    }
                })

        }

    }

    private fun buttonActiveState(appCompatButton: AppCompatButton) {
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        appCompatButton.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.blue)
        )

    }


    private fun buttonInActiveState(appCompatButton: AppCompatButton) {
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        appCompatButton.setBackgroundResource(
            R.drawable.drawable_editext

        )

    }

}