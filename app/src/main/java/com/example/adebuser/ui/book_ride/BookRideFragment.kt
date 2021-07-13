package com.example.adebuser.ui.book_ride


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import com.directions.route.*
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.databinding.FragmentBookRideBinding
import com.example.adebuser.ui.book_ride.ride_details.RideDetailsFragment
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.example.adebuser.utils.PermissionUtils
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.model.*
import java.util.*


class BookRideFragment : BaseFragment(), OnMapReadyCallback, RoutingListener, OnMapClickListener {
    private var param: String? = null
    private var type: String? = null

    private var mLocation: Location? = null
    private lateinit var locationCallback: LocationCallback
    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 999
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationManager: LocationManager? = null
    private var mLocationRequest: LocationRequest? = null

    private var startPoint: LatLng? = null
    private var endPoint: LatLng? = null
    private var polylines: ArrayList<Polyline>? = null


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


    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mLocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
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
                    val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())

                    val addresses: List<Address> = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )

                    val address: String =
                        addresses[0].getAddressLine(0)


                 //   binding.tvCurrentAddress.text = addresses[0].featureName
                      binding.tvCurrentAddress.text = address
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


        type = (activity as HomeScreenActivity).userPreferences.getTimeType()

        if (type.isNullOrEmpty()) {
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


    override fun onResume() {
        super.onResume()
        setBackgroundAccordingToType()
        startLocationUpdates()
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

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
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
        val center = CameraUpdateFactory.newLatLng(startPoint)
        val zoom = CameraUpdateFactory.zoomTo(16f)
        if (polylines != null) {
            polylines?.clear()
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null


        polylines = ArrayList<Polyline>()
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
                polylines?.add(polyline)
            }
        }


        //Add Marker on route starting position

        //Add Marker on route starting position
        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng)
        startMarker.title("My Location")
        googleMap?.addMarker(startMarker)

        //Add Marker on route ending position

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng)
        endMarker.title("Destination")
        googleMap?.addMarker(endMarker)




    }

    override fun onRoutingCancelled() {
    }

    override fun onMapClick(latLng: LatLng) {
        endPoint = latLng
        googleMap?.clear()
        startPoint = LatLng(
            mLocation!!.latitude,
            mLocation!!.longitude
        )
        val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            endPoint!!.latitude,
            endPoint!!.longitude,
            1
        )

        val address: String =
            addresses[0].getAddressLine(0)

        binding.tvDestinationAddress.text = address
        //start route finding
        findroutes(startPoint, endPoint)
    }


}