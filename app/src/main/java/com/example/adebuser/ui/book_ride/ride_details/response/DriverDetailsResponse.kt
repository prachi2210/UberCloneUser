package com.example.adebuser.ui.book_ride.ride_details.response

 data class DriverDetailsResponse(
    val msg: String,
    val rideInfo: RideInfo,
    val status: String
)

data class RideInfo(
    val bookingStatus: String,
    val carType: String,
    val chooseType: String,
    val coupon: String,
    val created_at: String,
    val driverLatitude: String,
    val driverLongitude: String,
    val driverName: String,
    val driverProfilePic: String,
    val driverRating: String,
    val driverRef: String,
    val dropOffLat: String,
    val dropOffLog: String,
    val dropOffName: String,
    val fareAmount: String,
    val gearType: String,
    val hourly: String,
    val id: String,
    val isFavorite: String,
    val latitude: String,
    val longitude: String,
    val paymentMode: String,
    val pickupLat: String,
    val pickupLog: String,
    val pickupName: String,
    val scheduleTime: String,
    val updated_at: String,
    val userName: String,
    val userProfilePic: String,
    val userRef: String
)