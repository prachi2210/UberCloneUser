package com.example.adebuser.ui.book_ride

data class DriverLocationResponse(
    val driverLocation: DriverLocation,
    val msg: String,
    val status: String
)

data class DriverLocation(
    val latitude: String,
    val longitude: String,
    val name: String,
    val updated_at: String,
    val userId: String,
    val userRef: String
)