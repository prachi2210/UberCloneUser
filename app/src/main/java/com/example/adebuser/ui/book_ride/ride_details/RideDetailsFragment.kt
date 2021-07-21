package com.example.adebuser.ui.book_ride.ride_details

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentRideDetailsBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.extensions.loadImage
import com.example.adebuser.ui.book_ride.BookRideViewModel
import com.example.adebuser.ui.book_ride.LocationViewModel
import com.example.adebuser.ui.book_ride.ride_details.response.RideInfo
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.ui.me.favourite_rider.FavoriteDriverViewModel
import com.example.adebuser.ui.rate.DriverRatingActivity
import com.example.adebuser.utils.Constants
import com.example.adebuser.utils.Status
import com.google.android.gms.maps.model.LatLng


class RideDetailsFragment : BaseFragment() {
    private var param: String? = null

    private var rideId: String? = null


    private var _binding: FragmentRideDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GetRideDetailsViewModel
    private lateinit var locationViewModel: LocationViewModel

    private var driverRef: String? = null
    private var driverPhoto: String? = null
    private var driverName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString("type")
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


        locationViewModel = activity?.run {
            ViewModelProvider(this).get(LocationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")




        binding.closeFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@RideDetailsFragment)
                .commit()
        }

        binding.cancelRide.setOnClickListener {

            showCancelConfirmationDialog()
//            replaceFragmentFull(BookRideFragment.newInstance("choose vehicle"), "booked")

        }


        setupViewModel()



        when (param) {
            "acceptBooking" -> {
                binding.showDriverDetails.visibility = View.VISIBLE
                binding.showAnim.visibility = View.GONE
                binding.closeFragment.text = getString(R.string.driver_on_way)

                hitDriverDetailsApi()
            }

            "rejectBooking" -> {
                replaceFragmentFull(BookRideFragment(), "book")


            }

            "ride started" -> {
                binding.showDriverDetails.visibility = View.VISIBLE
                binding.showAnim.visibility = View.GONE
                binding.closeFragment.text = getString(R.string.ride_start_notification)
                hitDriverDetailsApi()
            }

            "ride completed" -> {
                showDialog()
            }

            else -> {
                binding.showDriverDetails.visibility = View.GONE
                binding.showAnim.visibility = View.VISIBLE
                binding.closeFragment.text = getString(R.string.searching_driver)
            }
        }





        Handler(Looper.getMainLooper()).postDelayed({


        }, 8000)
    }

    private fun showCancelConfirmationDialog() {
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Alert!")
        builder.setMessage(getString(R.string.cancel_booking_alert))
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->

            hitCancelApi()


        }
        builder.setNegativeButton("Cancel")
        { dialog, which ->

            dialog.dismiss()

        }
        builder.show()
    }

    private fun hitCancelApi() {
        if (rideId != null) {
            viewModel.cancelTrip(
                rideId!!,
            ).observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            (activity as HomeScreenActivity).dismissDialog()

                            resource.data?.let { user ->
                                if (user.body()?.status.equals("success")) {
                                    replaceFragmentFull(
                                        BookRideFragment.newInstance(
                                            "booked",
                                            "ride canceled"
                                        ), "booked"
                                    )

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
                            //   (activity as HomeScreenActivity).showDialog()
                        }
                    }
                }
            })
        }
    }

    private fun showDialog() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this@RideDetailsFragment)
            .commit()

        val builder =
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Alert!")
        builder.setMessage(getString(R.string.add_review_message))
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            val intent = Intent(requireContext(), DriverRatingActivity::class.java)
            startActivity(intent)

        }
        builder.setNegativeButton("Cancel")
        { dialog, which ->

            replaceFragmentFull(BookRideFragment(), "book")


            dialog.dismiss()

        }
        builder.show()
    }


    private fun hitDriverDetailsApi() {
        viewModel.getRideDetails(
            (activity as HomeScreenActivity).userPreferences.getUserREf(),
        ).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as HomeScreenActivity).dismissDialog()

                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                binding.showDriverDetails.visibility = View.VISIBLE
                                binding.showAnim.visibility = View.GONE

                                rideId = user.body()!!.rideInfo.id
                                setLayout(user.body()!!.rideInfo)

                                driverName =  user.body()!!.rideInfo.driverName
                                driverPhoto =  user.body()!!.rideInfo.driverProfilePic
                                driverRef =  user.body()!!.rideInfo.driverRef

                                val pickUp = LatLng(
                                    user.body()!!.rideInfo.pickupLat.toDouble(),
                                    user.body()!!.rideInfo.pickupLat.toDouble()
                                )
                                val dropOff = LatLng(
                                    user.body()!!.rideInfo.dropOffLat.toDouble(),
                                    user.body()!!.rideInfo.dropOffLog.toDouble()
                                )

                                locationViewModel.setMapLatLng(pickUp, dropOff)

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
                        //   (activity as HomeScreenActivity).showDialog()
                    }
                }
            }
        })
    }


    private fun setLayout(rideInfo: RideInfo) {
        binding.tvName.text = rideInfo.driverName
        binding.tvPrice.text = "$ ".plus(rideInfo.fareAmount)
        binding.toAddress.text = rideInfo.dropOffName
        binding.FromAddress.text = rideInfo.pickupName
        binding.ivProfile.loadImage(Constants.PHOTO_BASE_URL+rideInfo.driverProfilePic)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(GetRideDetailsViewModel::class.java)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            RideDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("type", param1)

                }
            }
    }
}