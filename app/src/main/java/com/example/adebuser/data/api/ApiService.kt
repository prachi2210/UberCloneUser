package com.example.adebuser.data.api


import com.example.adebuser.ui.auth.response.LoginResponse
import com.example.adebuser.ui.auth.response.MessageResponse
import com.example.adebuser.ui.auth.response.SignUpResponse
import com.example.adebuser.ui.book_ride.DriverLocationResponse
import com.example.adebuser.ui.book_ride.UserStatusResponse
import com.example.adebuser.ui.book_ride.ride_details.response.DriverDetailsResponse
import com.example.adebuser.ui.me.favourite_rider.response.FavoriteDriverResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST("userRegister")
    suspend fun signUp(
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("deviceType") deviceType: RequestBody?,
        @Part("deviceToken") deviceToken: RequestBody?,
    ): Response<SignUpResponse>


    @Multipart
    @POST("userLogin")
    suspend fun login(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("FieldType") fieldType: RequestBody?,
        @Part("deviceType") deviceType: RequestBody?,
        @Part("deviceToken") deviceToken: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?
    ): Response<LoginResponse>


    @Multipart
    @POST("userOut")
    suspend fun logout(
        @Part("userRef") userRef: RequestBody?
    ): Response<MessageResponse>

    @Multipart
    @POST("otpVerify")
    suspend fun otpVerify(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("otp") otp: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("ForgetPassword")
    suspend fun forgotPassword(
        @Part("FieldType") fieldType: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("resendOtp")
    suspend fun resendOtp(
        @Part("phoneNumber") phoneNumber: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("CreateNewPassword")
    suspend fun createNewPassword(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("getFavoriteDriver")
    suspend fun getFavoriteDrivers(
        @Part("userRef") userRef: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
        @Part("gearType") gearType: RequestBody?,
        @Part("carType") carType: RequestBody?
    ): Response<FavoriteDriverResponse>


    @Multipart
    @POST("bookingConfirmation")
    suspend fun confirmBooking(
        @Part("userRef") userRef: RequestBody,
        @Part("chooseType") chooseType: RequestBody,
        @Part("pickupLat") pickupLat: RequestBody,
        @Part("pickupLog") pickupLong: RequestBody,
        @Part("dropOffLat") dropOffLat: RequestBody,
        @Part("dropOffLog") dropOffLong: RequestBody,
        @Part("gearType") gearType: RequestBody,
        @Part("carType") carType: RequestBody,
        @Part("scheduleTime") scheduleTime: RequestBody,
        @Part("paymentMode") paymentMode: RequestBody,
        @Part("fareAmount") fareAmount: RequestBody,
        @Part("pickupName") pickupName: RequestBody,
        @Part("dropOffName") dropOffName: RequestBody,
        @Part("coupon") coupon: RequestBody,
        @Part("hourly") hourly: RequestBody,
        @Part("favoriteDriverRef") favoriteDriverRef: RequestBody,
    ): Response<MessageResponse>


    @Multipart
    @POST("getRideConfirmation")
    suspend fun getDriverDetails(
        @Part("userRef") userRef: RequestBody?
    ): Response<DriverDetailsResponse>


    @Multipart
    @POST("getDriverLocation")
    suspend fun getDriverLocation(
        @Part("driverRef") driverRef: RequestBody?
    ): Response<DriverLocationResponse>


@Multipart
    @POST("getUserSatus")
    suspend fun getUserStatus(
        @Part("userRef") userRef: RequestBody?
    ): Response<UserStatusResponse>

@Multipart
    @POST("cancelTrip")
    suspend fun cancelTrip(
        @Part("rideId") userRef: RequestBody?
    ): Response<UserStatusResponse>


    @Multipart
    @POST("getPreviousBookings")
    suspend fun getPreviousBookings(
        @Part("userRef") userRef: RequestBody?,
        @Part("type") type: RequestBody?
    ): Response<UserStatusResponse>

}






