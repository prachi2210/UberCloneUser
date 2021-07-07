package com.example.adebuser.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.extensions.getMultipart
import com.example.adebuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AuthViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun login(
        phoneNumber: String,
        password: String,
        token: String
    ) = liveData(Dispatchers.IO) {

        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        val userPassword = password.toRequestBody("multipart/form-data".toMediaType())
        val fieldType = "phoneNumber".toRequestBody("multipart/form-data".toMediaType())
        val OSType = "Android".toRequestBody("multipart/form-data".toMediaType())
        val deviceToken = token.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.getLogin(
                        userPhoneNumber,
                        userPassword,
                        fieldType,
                        OSType,
                        deviceToken
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun signUp(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        token: String,
    ) = liveData(Dispatchers.IO) {

        val userName = name.toRequestBody("multipart/form-data".toMediaType())
        val userEmail = email.toRequestBody("multipart/form-data".toMediaType())
        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        val userPassword = password.toRequestBody("multipart/form-data".toMediaType())
        val OSType = "Android".toRequestBody("multipart/form-data".toMediaType())
        val deviceToken = token.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.signUp(
                        userName,
                        userEmail,
                        userPhoneNumber,
                        userPassword,
                        OSType,
                        deviceToken
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun forgotPassword(phoneNumber: String) = liveData(Dispatchers.IO)
    {

        val fieldType = "phoneNumber".toRequestBody("multipart/form-data".toMediaType())
        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.forgotPassword(
                        fieldType, userPhoneNumber
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun resendOtp(phoneNumber: String) = liveData(Dispatchers.IO)
    {

        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.resendOtp(
                        userPhoneNumber
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun otpVerify(phoneNumber: String, otp: String) = liveData(Dispatchers.IO)
    {
        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        val userOtp = otp.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.otpVerify(
                        userPhoneNumber, userOtp
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun createNewPassword(userRef: String, phoneNumber: String) = liveData(Dispatchers.IO)
    {
        val userPhoneNumber = userRef.toRequestBody("multipart/form-data".toMediaType())
        val userPassword = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.createNewPassword(
                        userPhoneNumber, userPassword
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun logout(userRef: String) = liveData(Dispatchers.IO)
    {
        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.logout(
                        userRef
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}
