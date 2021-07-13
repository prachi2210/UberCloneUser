package com.example.adebuser.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

class ApiHelper(private val apiService: ApiService) {

    suspend fun getLogin(
        userEmail: RequestBody,
        userPassword: RequestBody,
        fieldType: RequestBody,
        OSType: RequestBody,
        deviceToken: RequestBody,
        latitude: RequestBody?,
        longitude: RequestBody?
    ) = apiService.login(
        userEmail,
        userPassword,
        fieldType,
        OSType,
        deviceToken,
        latitude,
        longitude
    )


    suspend fun signUp(
        name: RequestBody?,
        email: RequestBody?,
        phoneNumber: RequestBody?,
        password: RequestBody?,
        deviceType: RequestBody?,
        deviceToken: RequestBody?,
    ) = apiService.signUp(name, email, phoneNumber, password, deviceType, deviceToken)


    suspend fun logout(
        userRef: RequestBody?,

        ) = apiService.logout(userRef)


    suspend fun otpVerify(
        phoneNumber: RequestBody,
        otp: RequestBody

    ) = apiService.otpVerify(phoneNumber, otp)


    suspend fun forgotPassword(
        fieldType: RequestBody,
        phoneNumber: RequestBody

    ) = apiService.forgotPassword(fieldType, phoneNumber)


    suspend fun resendOtp(
        phoneNumber: RequestBody?
    ) = apiService.resendOtp(phoneNumber)


    suspend fun createNewPassword(
        phoneNumber: RequestBody?,
        password: RequestBody?
    ) = apiService.createNewPassword(phoneNumber, password)

    suspend fun getFavoriteDriver(
        userRef: RequestBody?
    ) = apiService.getFavoriteDrivers(userRef)

    suspend fun confirmBooking(
        userRef: RequestBody,
        chooseType: RequestBody,
        pickupLat: RequestBody,
        pickupLong: RequestBody,
        dropOffLat: RequestBody,
        dropOffLong: RequestBody,
        gearType: RequestBody,
        carType: RequestBody,
        scheduleTime: RequestBody,
        paymentMode: RequestBody,
        fareAmount: RequestBody
    ) = apiService.confirmBooking(
        userRef,
        chooseType,
        pickupLat,
        pickupLong,
        dropOffLat,
        dropOffLong,
        gearType,
        carType,
        scheduleTime,
        paymentMode,
        fareAmount
    )


}



