package com.example.adebuser.ui.book_ride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class BookRideViewModel(private val appRepository: AppRepository) : ViewModel() {


    fun bookRide(
        userRef: String,
        chooseType: String,
        pickupLat: String,
        pickupLong: String,
        dropOffLat: String,
        dropOffLong: String,
        gearType: String,
        carType: String,
        scheduleTime: String,
        paymentMode: String,
        fareAmount: String,
    ) = liveData(Dispatchers.IO) {

        val userReference = userRef.toRequestBody("multipart/form-data".toMediaType())
        val cabTimeType = chooseType.toRequestBody("multipart/form-data".toMediaType())
        val userLat = pickupLat.toRequestBody("multipart/form-data".toMediaType())
        val userLong = pickupLong.toRequestBody("multipart/form-data".toMediaType())
        val dropOffLatitude = dropOffLat.toRequestBody("multipart/form-data".toMediaType())
        val dropOffLongitude = dropOffLong.toRequestBody("multipart/form-data".toMediaType())
        val carGearType = gearType.toRequestBody("multipart/form-data".toMediaType())
        val typeOfCar = carType.toRequestBody("multipart/form-data".toMediaType())
        val selectedTime = scheduleTime.toRequestBody("multipart/form-data".toMediaType())
        val paymentMethod = paymentMode.toRequestBody("multipart/form-data".toMediaType())
        val totalCharges = fareAmount.toRequestBody("multipart/form-data".toMediaType())

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
                        totalCharges
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}