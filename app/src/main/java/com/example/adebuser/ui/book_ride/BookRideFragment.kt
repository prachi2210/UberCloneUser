package com.example.adebuser.ui.book_ride


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.directions.route.*
import com.example.adebuser.HomeScreenActivity
import com.example.adebuser.R
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.databinding.FragmentBookRideBinding
import com.example.adebuser.ui.book_ride.ride_details.RideDetailsFragment
import com.example.adebuser.ui.book_ride.select_car.CarTypeFragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener
import com.google.android.gms.maps.model.*
import java.util.*


class BookRideFragment : BaseFragment(), OnMapReadyCallback, RoutingListener,
    android.location.LocationListener {
    private var param: String? = null
    private var type: String? = null

    private lateinit var mLocation: Location
    private lateinit var locationCallback: LocationCallback
    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val LOCATION_REQUEST_CODE = 23
    var locationPermission = false
    private var startPoint: LatLng? =  LatLng(
        30.7411,
        76.7790
    )
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()

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

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity() , arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            locationPermission = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
          LOCATION_REQUEST_CODE -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //if permission granted.
                    locationPermission = true

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }    }

    private fun getMyLocation() {


        googleMap!!.setMyLocationEnabled(true)

        googleMap!!.setOnMyLocationChangeListener { location ->
            mLocation = location
            val ltlng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 16f
            )
            googleMap!!.animateCamera(cameraUpdate)
        }


        //get destination location when user click on map
        googleMap?.setOnMapClickListener(OnMapClickListener { latLng ->
            endPoint = latLng
            googleMap?.clear()
            startPoint = LatLng(
                mLocation.latitude,
                mLocation.longitude
            )
            //start route finding
            findroutes(startPoint, endPoint)
        })
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
        if (locationPermission) {
            getMyLocation()
        }

    }





    override fun onRoutingFailure(p0: RouteException?) {

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
                polyOptions.color(ContextCompat.getColor(requireContext(), R.color.blue))
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

    override fun onLocationChanged(location: Location) {
        mLocation = location
        val ltlng = LatLng(location.getLatitude(), location.getLongitude())
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            ltlng, 16f
        )
        googleMap?.animateCamera(cameraUpdate)
    }


}