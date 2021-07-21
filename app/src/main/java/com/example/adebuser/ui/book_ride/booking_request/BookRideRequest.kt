package com.example.adebuser.ui.book_ride.booking_request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookRideRequest(
    var userRef: String? = null,
    var chooseType: String? = null,
    var pickupLat: String? = null,
    var pickupLong: String? = null,
    var dropOffLat: String? = null,
    var dropOffLong: String? = null,
    var gearType: String? = null,
    var carType: String? = null,
    var scheduleTime: String? = null,
    var paymentMode: String? = null,
    var fareAmount: String? = null,
    var coupon: String? = "",
    var hourly: String? = "",
    var favoriteDriverRef: String? = "",
    var pickUpName: String? = null,
    var dropOffName: String? = null,
) : Parcelable



