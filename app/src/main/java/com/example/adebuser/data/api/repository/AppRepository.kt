package com.example.adebuser.data.api.repository

import com.example.adebuser.data.api.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository(var apiHelper: ApiHelper) {


    suspend fun getLogin(
        userEmail: RequestBody,
        userPassword: RequestBody,
        fieldType: RequestBody,
        OSType: RequestBody,
        deviceToken: RequestBody,
        latitude: RequestBody?,
        longitude: RequestBody?
    ) = apiHelper.getLogin(
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
    ) = apiHelper.signUp(name, email, phoneNumber, password, deviceType, deviceToken)


    suspend fun logout(
        userRef: RequestBody?,

        ) = apiHelper.logout(userRef)

    suspend fun otpVerify(
        phoneNumber: RequestBody,
        otp: RequestBody
    ) = apiHelper.otpVerify(
        phoneNumber, otp
    )


    suspend fun forgotPassword(
        fieldType: RequestBody,
        phoneNumber: RequestBody

    ) = apiHelper.forgotPassword(fieldType, phoneNumber)

    suspend fun resendOtp(
        phoneNumber: RequestBody?
    ) = apiHelper.resendOtp(phoneNumber)


    suspend fun createNewPassword(
        phoneNumber: RequestBody?,
        password: RequestBody?
    ) = apiHelper.createNewPassword(phoneNumber, password)


    suspend fun getFavoriteDriver(
        userRef: RequestBody?,
        latitude: RequestBody?,
        longitude: RequestBody?,
        gearType: RequestBody?,
        carType: RequestBody?
    ) = apiHelper.getFavoriteDriver(userRef, latitude, longitude, gearType, carType)


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
        fareAmount: RequestBody,
        pickupName: RequestBody,
        dropOffName: RequestBody,
        coupon: RequestBody,
        hourly: RequestBody,
        favoriteDriverRef: RequestBody,
    ) = apiHelper.confirmBooking(
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
        fareAmount,
        pickupName,
        dropOffName,
        coupon,
        hourly,
        favoriteDriverRef
    )

    suspend fun getRideConfirmation(
        userRef: RequestBody?
    ) = apiHelper.getRideConfirmation(userRef)

    suspend fun getDriverLocation(
        driverRef: RequestBody?
    ) = apiHelper.getDriverLocation(driverRef)


    suspend fun userStatus(
        userRef: RequestBody?
    ) = apiHelper.userStatus(userRef)

    suspend fun cancelTrip(
        rideId: RequestBody?
    ) = apiHelper.cancelTrip(rideId)
}


