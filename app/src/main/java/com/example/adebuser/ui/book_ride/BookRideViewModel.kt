package com.example.adebuser.ui.book_ride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class BookRideViewModel(private val appRepository: AppRepository) : ViewModel() {


    fun bookRide(
        bookRideRequest: BookRideRequest
    ) = liveData(Dispatchers.IO) {

        val userReference =
            bookRideRequest.userRef!!.toRequestBody("multipart/form-data".toMediaType())
        val cabTimeType =
            bookRideRequest.chooseType!!.toRequestBody("multipart/form-data".toMediaType())
        val userLat = bookRideRequest.pickupLat!!.toRequestBody("multipart/form-data".toMediaType())
        val userLong =
            bookRideRequest.pickupLong!!.toRequestBody("multipart/form-data".toMediaType())
        val dropOffLatitude =
            bookRideRequest.dropOffLat!!.toRequestBody("multipart/form-data".toMediaType())
        val dropOffLongitude =
            bookRideRequest.dropOffLong!!.toRequestBody("multipart/form-data".toMediaType())
        val carGearType =
            bookRideRequest.gearType!!.toRequestBody("multipart/form-data".toMediaType())
        val typeOfCar = bookRideRequest.carType!!.toRequestBody("multipart/form-data".toMediaType())
        val selectedTime =
            bookRideRequest.scheduleTime!!.toRequestBody("multipart/form-data".toMediaType())
        val paymentMethod =
            bookRideRequest.paymentMode!!.toRequestBody("multipart/form-data".toMediaType())
        val totalCharges =
            bookRideRequest.fareAmount!!.toRequestBody("multipart/form-data".toMediaType())
        val pickUpName =
            bookRideRequest.pickUpName!!.toRequestBody("multipart/form-data".toMediaType())
        val dropOffName =
            bookRideRequest.dropOffName!!.toRequestBody("multipart/form-data".toMediaType())
        val coupon =
            bookRideRequest.coupon!!.toRequestBody("multipart/form-data".toMediaType())
        val hourly =
            bookRideRequest.hourly!!.toRequestBody("multipart/form-data".toMediaType())
        val favDriver =
            bookRideRequest.favoriteDriverRef!!.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.confirmBooking(
                        userReference,
                        cabTimeType,
                        userLat,
                        userLong,
                        dropOffLatitude,
                        dropOffLongitude,
                        carGearType,
                        typeOfCar,
                        selectedTime,
                        paymentMethod,
                        totalCharges,
                        pickUpName,
                        dropOffName,
                        coupon,
                        hourly,
                        favDriver
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}