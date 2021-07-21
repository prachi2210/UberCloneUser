package com.example.adebuser.ui.book_ride.ride_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class GetRideDetailsViewModel(private val appRepository: AppRepository) : ViewModel() {


    fun getRideDetails(
        userRef: String,
    ) = liveData(Dispatchers.IO) {
        val userReference = userRef.toRequestBody("multipart/form-data".toMediaType())



        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.getRideConfirmation(
                        userReference
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun cancelTrip(
        rideId: String,
    ) = liveData(Dispatchers.IO) {
        val rideRef = rideId.toRequestBody("multipart/form-data".toMediaType())



        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.cancelTrip(
                        rideRef
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}