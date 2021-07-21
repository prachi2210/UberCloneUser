package com.example.adebuser.ui.book_ride


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.directions.route.*
import com.example.adebuser.R
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.databinding.FragmentBookRideBinding
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.ui.book_ride.ride_details.RideDetailsFragment
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.ui.rate.DriverRatingActivity
import com.example.adebuser.utils.AnimationUtil
import com.example.adebuser.utils.PermissionUtils
import com.example.adebuser.utils.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.model.*
import java.util.*


class BookRideFragment : BaseFragment(), OnMapReadyCallback, RoutingListener, OnMapClickListener {
    private var param: String? = null
    private var type: String? = null
    private var lastRideStatus: String? = null

    private var mLocation: Location? = null
    private lateinit var locationCallback: LocationCallback
    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 999
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationManager: LocationManager? = null
    private var mLocationRequest: LocationRequest? = null

    private var pickUpName: String? = null
    private var destinationName: String? = null


    private var startPoint: LatLng? = null
    private var endPoint: LatLng? = null
    private var polyLines: ArrayList<Polyline>? = null
    private lateinit var bookRideRequest: BookRideRequest

    private var _binding: FragmentBookRideBinding? = null
    private val binding get() = _binding!!
    private var mapListener: OnMapClickListener? = null

    private var driverMarker: Marker? = null

    private lateinit var viewModel: LocationViewModel

    private var bookingStatus: String? = null
    private var driverRef: String? = null
    private var driverPhoto: String? = null
    private var driverName: String? = null

    private lateinit var userStatusViewModel: GetUserBookingStatusViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString("status")
            bookingStatus = it.getString("bookingStatus")
            driverPhoto = it.getString("driverPhoto")
            driverRef = it.getString("driverRef")
            driverName = it.getString("driverName")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookRideBinding.inflate(inflater, container, false)

        mMapView = binding.mapView
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume()
        try {
            MapsInitializer.initialize(requireContext().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView!!.getMapAsync(this)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mapListener = activity as OnMapClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mLocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest!!,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getLocation() {
        mLocationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)


    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        mLocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationCallback = object : LocationCallback() {
            @SuppressLint("MissingPermission")
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    mLocation = location

                    googleMap!!.isMyLocationEnabled = true
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())

                    val addresses: List<Address> = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )

                    val address: String =
                        addresses[0].getAddressLine(0)

                    pickUpName = address
                    //   binding.tvCurrentAddress.text = addresses[0].featureName
                    binding.tvCurrentAddress.text = pickUpName
                    val ltlng = LatLng(location.latitude, location.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        ltlng, 16f
                    )
                    googleMap!!.animateCamera(cameraUpdate)

                }
            }
        }


//      fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(requireActivity())
//        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
//            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//
//        fusedLocationProviderClient.requestLocationUpdates(
//            locationRequest,
//            object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult) {
//                    super.onLocationResult(locationResult)
//                    for (location in locationResult.locations) {
//
//                        mLocation = location
//
//                        googleMap!!.isMyLocationEnabled = true
//                        val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
//
//                        val addresses: List<Address> = geocoder.getFromLocation(
//                            location.latitude,
//                            location.longitude,
//                            1
//                        )
//
//                        val address: String =
//                            addresses[0].getAddressLine(0)
//
//
//                      binding.tvCurrentAddress.text = addresses[0].featureName
//                       // binding.tvCurrentAddress.text = address
//                        val ltlng = LatLng(location.latitude, location.longitude)
//                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                            ltlng, 16f
//                        )
//                        googleMap!!.animateCamera(cameraUpdate)
//
//                    }
//                    // Few more things we can do here:
//                    // For example: Update the location of user on server
//                }
//            },
//            Looper.myLooper()
        //       )


    }
//


    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(requireContext()) -> {
                when {
                    PermissionUtils.isLocationEnabled(requireContext()) -> {
                        getLocation()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(requireContext())
                    }
                }
            }
            else -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookRideRequest = BookRideRequest()



        viewModel = this.run {
            ViewModelProvider(this).get(LocationViewModel::class.java)
        }




        type = (activity as HomeScreenActivity).userPreferences.getTimeType()

        if (type.isNullOrEmpty()) {
            type = "now"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
        }



        if (param == "booked") {

            when (bookingStatus) {
                "acceptBooking" -> {

                    binding.tvOpenFragment.text = getString(R.string.driver_on_way)

                    openFragmentSmall(RideDetailsFragment.newInstance(bookingStatus!!), "ride")
                }

                "rejectBooking" -> {
                    binding.tvOpenFragment.text =
                        getString(R.string.choose_your_car_and_gear_type)
                }

                "ride started" -> {
                    binding.tvOpenFragment.text = getString(R.string.ride_start_notification)
                }

                "ride completed" -> {
                    showRideCompletedDialog()
                    param = ""
                }

                "ride canceled" -> {
                    binding.tvOpenFragment.text = getString(R.string.choose_your_car_and_gear_type)
                }


                else -> {
                    binding.tvOpenFragment.text = getString(R.string.searching_driver)
                }
            }


        } else {
            binding.tvOpenFragment.text = getString(R.string.choose_your_car_and_gear_type)
        }


        binding.tvOpenFragment.setOnClickListener {


            if (lastRideStatus == "ride completed" || lastRideStatus.isNullOrEmpty() || lastRideStatus == "ride canceled" || lastRideStatus == "rejectBooking") {

                bookRideRequest.userRef =
                    (activity as HomeScreenActivity).userPreferences.getUserREf()
                bookRideRequest.pickupLat = mLocation?.latitude.toString()
                bookRideRequest.pickupLong = mLocation?.longitude.toString()
                bookRideRequest.pickUpName = pickUpName
                bookRideRequest.dropOffName = destinationName
                openFragmentSmall(CarTypeFragment.newInstance(type!!, bookRideRequest), "car")
            } else {

                openFragmentSmall(RideDetailsFragment.newInstance(bookingStatus!!), "ride")
            }
        }

        setUpLocationListener()
        setButtonsState()

//        googleMap?.setOnMapClickListener(OnMapClickListener { latLng ->
//            endPoint = latLng
//            googleMap?.clear()
//            startPoint = LatLng(
//                mLocation!!.latitude,
//                mLocation!!.longitude
//            )
//
//            //start route finding
//            findroutes(startPoint, endPoint)
//        })
    }

    private fun setUserStatusViewModel() {
        userStatusViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(GetUserBookingStatusViewModel::class.java)




        userStatusViewModel.getUserStatus(
            (activity as HomeScreenActivity).userPreferences.getUserREf(),
        ).observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        //    (activity as HomeScreenActivity).dismissDialog()

                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {

                                lastRideStatus = user.body()?.lastRideStatus

                                if (bookingStatus == null) {
                                    bookingStatus = lastRideStatus
                                }


                                if (param != "booked") {
                                    when (user.body()?.lastRideStatus) {

                                        "acceptBooking" -> {

                                            binding.tvOpenFragment.text =
                                                getString(R.string.driver_on_way)

                                            openFragmentSmall(
                                                RideDetailsFragment.newInstance(
                                                    bookingStatus!!
                                                ), "ride"
                                            )
                                        }

                                        "rejectBooking" -> {
                                            binding.tvOpenFragment.text =
                                                getString(R.string.choose_your_car_and_gear_type)

                                        }

                                        "ride started" -> {
                                            binding.tvOpenFragment.text =
                                                getString(R.string.ride_start_notification)
                                        }

                                        "bookingConfirmation" -> {
                                            binding.tvOpenFragment.text =
                                                getString(R.string.searching_driver)
                                        }
                                        else -> {
                                            binding.tvOpenFragment.text =
                                                getString(R.string.choose_your_car_and_gear_type)
                                        }

                                    }
                                }
                            } else {
                                (activity as HomeScreenActivity).setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    Status.ERROR -> {
                        // (activity as HomeScreenActivity).dismissDialog()
                        (activity as HomeScreenActivity).setError(it.message.toString())

                    }
                    Status.LOADING -> {
                        //    (activity as HomeScreenActivity).showDialog()
                    }
                }
            }
        })


    }

    private fun showRideCompletedDialog() {

        val builder =
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Alert!")
        builder.setMessage(getString(R.string.add_review_message))
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            val intent = Intent(requireContext(), DriverRatingActivity::class.java)
            intent.putExtra("driverName", driverName)
            intent.putExtra("driverPhoto", driverPhoto)
            intent.putExtra("driverRef", driverRef)
            startActivity(intent)

        }
        builder.setNegativeButton("Cancel")
        { dialog, which ->

            dialog.dismiss()

        }
        builder.show()
    }

    private fun setAnimation(startPoint: LatLng?) {
        AnimationUtil.animateMarkerTo(driverMarker, startPoint)

        val cameraPosition = CameraPosition.Builder()
            .target(startPoint!!) // Sets the center of the map to location user
            .zoom(14f) // Sets the zoom
            .bearing(90f) // Sets the orientation of the camera to east
            .tilt(40f) // Sets the tilt of the camera to 30 degrees
            .build() // Creates a CameraPosition from the builder

        if (googleMap != null) {
            googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }


    override fun onResume() {
        super.onResume()
        setBackgroundAccordingToType()
        setUserStatusViewModel()
        when {
            PermissionUtils.isAccessFineLocationGranted(requireContext()) -> {
                when {
                    PermissionUtils.isLocationEnabled(requireContext()) -> {
                        startLocationUpdates()

                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(requireContext())
                    }
                }
            }
            else -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }


        viewModel.setPickupLatLng.observe(this, androidx.lifecycle.Observer {

            Log.e("HIII", viewModel.setPickupLatLng.value.toString())

        })

        viewModel.setDropOffLatLng.observe(this, androidx.lifecycle.Observer {
            Log.e("HIII", viewModel.setDropOffLatLng.value.toString())
        })
    }


    private fun setButtonsState() {
        binding.btnNow.setOnClickListener {
            type = "now"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (lastRideStatus == "ride completed" || lastRideStatus.isNullOrEmpty()) {

                bookRideRequest.userRef =
                    (activity as HomeScreenActivity).userPreferences.getUserREf()
                bookRideRequest.pickupLat = mLocation?.latitude.toString()
                bookRideRequest.pickupLong = mLocation?.longitude.toString()
                bookRideRequest.pickUpName = pickUpName
                bookRideRequest.dropOffName = destinationName
                replaceFragmentSmall(
                    CarTypeFragment.newInstance(type!!, bookRideRequest),
                    "car"
                )
            } else {
                replaceFragmentSmall(RideDetailsFragment.newInstance(bookingStatus!!), "ride")
            }
        }


        binding.btnDay.setOnClickListener {
            type = "day"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (lastRideStatus == "ride completed" || lastRideStatus.isNullOrEmpty()) {

                bookRideRequest.userRef =
                    (activity as HomeScreenActivity).userPreferences.getUserREf()
                bookRideRequest.pickupLat = mLocation?.latitude.toString()
                bookRideRequest.pickupLong = mLocation?.longitude.toString()
                bookRideRequest.pickUpName = pickUpName
                bookRideRequest.dropOffName = destinationName
                replaceFragmentSmall(
                    CarTypeFragment.newInstance(type!!, bookRideRequest),
                    "car"
                )
            } else {
                replaceFragmentSmall(RideDetailsFragment.newInstance(bookingStatus!!), "ride")
            }

        }



        binding.btnRegular.setOnClickListener {
            type = "regular"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (lastRideStatus == "ride completed" || lastRideStatus.isNullOrEmpty()) {

                bookRideRequest.userRef =
                    (activity as HomeScreenActivity).userPreferences.getUserREf()
                bookRideRequest.pickupLat = mLocation?.latitude.toString()
                bookRideRequest.pickupLong = mLocation?.longitude.toString()
                bookRideRequest.pickUpName = pickUpName
                bookRideRequest.dropOffName = destinationName
                replaceFragmentSmall(
                    CarTypeFragment.newInstance(type!!, bookRideRequest),
                    "car"
                )
            } else {
                replaceFragmentSmall(RideDetailsFragment.newInstance(bookingStatus!!), "ride")
            }

        }

        binding.btnHourly.setOnClickListener {
            type = "hourly"
            (activity as HomeScreenActivity).userPreferences.saveCabTime(type!!)
            setBackgroundAccordingToType()
            if (lastRideStatus == "ride completed" || lastRideStatus.isNullOrEmpty()) {

                bookRideRequest.userRef =
                    (activity as HomeScreenActivity).userPreferences.getUserREf()
                bookRideRequest.pickupLat = mLocation?.latitude.toString()
                bookRideRequest.pickupLong = mLocation?.longitude.toString()
                bookRideRequest.pickUpName = pickUpName
                bookRideRequest.dropOffName = destinationName
                replaceFragmentSmall(
                    CarTypeFragment.newInstance(type!!, bookRideRequest),
                    "car"
                )
            } else {
                replaceFragmentSmall(RideDetailsFragment.newInstance(bookingStatus!!), "ride")
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
        fun newInstance(param: String, bookingType: String) =
            BookRideFragment().apply {
                arguments = Bundle().apply {
                    putString("status", param)
                    putString("bookingStatus", bookingType)

                }
            }

        fun newInstanceForRating(
            param: String,
            bookingType: String,
            driverRef: String?,
            driverName: String?,
            driverPhoto: String?
        ) = BookRideFragment().apply {
            arguments = Bundle().apply {
                putString("status", param)
                putString("bookingStatus", bookingType)
                putString("driverRef", driverRef)
                putString("driverName", driverName)
                putString("driverPhoto", driverPhoto)
            }
        }
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //if permission granted.
                    getLocation()

                }
                return
            }
        }
    }


    private fun findroutes(startPoint: LatLng?, endPoint: LatLng?) {
        if (startPoint == null || endPoint == null) {

            Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_LONG).show()
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(startPoint, endPoint)
                .key("AIzaSyCh8iJgNF3h-edODUQBvPBPq2TaNYyIWsQ") //also define your api key here.
                .build()
            routing.execute()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap!!.setOnMapClickListener(this)
        getLocation()


    }


    override fun onRoutingFailure(p0: RouteException?) {

        Log.e("PRACHI", p0!!.printStackTrace().toString())
    }

    override fun onRoutingStart() {
    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {

        if (polyLines != null) {
            polyLines?.clear()
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null


        polyLines = ArrayList<Polyline>()
        //add route(s) to the map using polyline
        //add route(s) to the map using polyline
        for (i in route!!.indices) {
            if (i == shortestRouteIndex) {
                polyOptions.color(ContextCompat.getColor(requireContext(), R.color.text_blue))
                polyOptions.width(14f)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyline: Polyline = googleMap!!.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                polyLines?.add(polyline)
            }
        }


        //Add Marker on route starting position

        //Add Marker on route starting position
        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng!!)
        startMarker.title("My Location")
        // googleMap?.addMarker(startMarker)

        driverMarker = googleMap?.addMarker(startMarker)
        //Add Marker on route ending position

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        endMarker.title("Destination")
        googleMap?.addMarker(endMarker)


//        AnimationUtil.animateMarkerTo(driverMarker, polylineEndLatLng)
//        val cameraPosition = CameraPosition.Builder()
//            .target(polylineEndLatLng) // Sets the center of the map to location user
//            .zoom(15f) // Sets the zoom
//            .bearing(30f) // Sets the orientation of the camera to east
//            .tilt(40f) // Sets the tilt of the camera to 30 degrees
//            .build() // Creates a CameraPosition from the builder
//
//        if (googleMap != null) {
//            googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//        }

    }

    override fun onRoutingCancelled() {
    }

    override fun onMapClick(latLng: LatLng) {

        //   viewModel.setLatLong(latLng)


        endPoint = latLng
        googleMap?.clear()
        startPoint = LatLng(
            mLocation!!.latitude,
            mLocation!!.longitude
        )
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            endPoint!!.latitude,
            endPoint!!.longitude,
            1
        )

        val address: String =
            addresses[0].getAddressLine(0)

        bookRideRequest.dropOffLat = endPoint?.latitude.toString()
        bookRideRequest.dropOffLong = endPoint?.longitude.toString()

        destinationName = address

        binding.tvDestinationAddress.text = destinationName
        //start route finding
        findroutes(startPoint, endPoint)

        mapListener?.onMapClick(
            LatLng(
                mLocation!!.latitude,
                mLocation!!.longitude
            ),
            latLng,
            type,
            pickUpName.toString(),
            destinationName.toString()
        )
    }

    interface OnMapClickListener {
        fun onMapClick(
            latLngPickUp: LatLng,
            latLngDropOff: LatLng,
            type: String?,
            pickUpName: String,
            dropOffName: String
        )

    }
}