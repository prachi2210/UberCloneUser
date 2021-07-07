package com.example.adebuser.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.collections.ArrayList


class PreferenceManager(context: Context) {
    private val PREFS_FILE_NAME = "ADEB User"
    private val USER_ID = "user id"
    private val USER_REF = "user ref"
    private val USER_Email = "user email"
    private val USER_NAME = "user name"
    private val DEVICE_TOKEN = "device token"
    private val CHURCH = "church name"
    private val DIET = "diet name"
    private val MINISTRY = "ministry"
    private val NAME = "name"
    private val ROLE = "role"
    private val PHOTO = "photo"
    private val GENDER = "gender"
    private val HAIR_COLOR = "hair color"
    private val LANGUAGE = "language"
    private val HEIGHT = "height"
    private val LATITUDE = "latitude"
    private val LONGITUDE = "longitude"
    private val VIEWS = "views"
    private val LIKES = "likes"
    private val SMOKES = "smokes"
    private val DRINKS = "drinks"
    private val EDUCATION = "education"
    private val BODYTYPE = "bodytype"
    private val ETHNICITY = "ethnicity"
    private val RELIGION = "religion"
    private val MYINTEREST = "myInterest"
    private val PET = "pet"
    private val COUNTRY = "country"
    private val BIRTHDAY = "birthday"
    private val PHONENUMBER = "phoneNumber"
    private val MANAGENOTIFICATIONS = "notifications"
    private val LOCATION = "location"
    private val ABOUTME = "aboutme"
    private val PHOTOLIST = "photolist"
    private val PHOTO1 = "photo1"
    private val PHOTO2 = "photo2"
    private val PHOTO3 = "photo3"
    private val PHOTO4 = "photo4"
    private val PHOTO5 = "photo5"
    private val PHOTO6 = "photo6"
    private val VIDEO = "video_url"


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor = sharedPreferences.edit()


    fun clearPrefs() {
        mEditor.apply {
            clear()
            commit()
        }
    }


    fun saveUserEmail(email: String) {
        mEditor.putString(USER_Email, email)
        mEditor.apply()
    }


    fun saveUserChurchName(church: String) {
        mEditor.putString(CHURCH, church)
        mEditor.apply()
    }

    fun saveUserDietName(diet: String) {
        mEditor.putString(DIET, diet)
        mEditor.apply()
    }


    fun saveAboutMe(aboutMe: String) {
        mEditor.putString(ABOUTME, aboutMe)
        mEditor.apply()
    }


    fun saveLocation(location: String) {
        mEditor.putString(LOCATION, location)
        mEditor.apply()
    }


    fun saveCurrentLatitude(latitude: String) {
        mEditor.putString(LATITUDE, latitude)
        mEditor.apply()
    }


    fun saveCurrentLongitude(longitude: String) {
        mEditor.putString(LONGITUDE, longitude)
        mEditor.apply()
    }

    fun saveUserMinistry(ministry: String) {
        mEditor.putString(MINISTRY, ministry)
        mEditor.apply()
    }


    fun saveName(name: String?) {
        mEditor.putString(NAME, name)
        mEditor.apply()
    }


    fun saveUserRef(user_id: String?) {

        mEditor.putString(USER_REF, user_id)
        mEditor.apply()
    }

    fun saveUserID(user_id: String?) {

        mEditor.putString(USER_ID, user_id)
        mEditor.apply()
    }


    fun saveDeviceToken(userToken: String?) {

        mEditor.putString(DEVICE_TOKEN, userToken)
        mEditor.apply()
    }


    fun savePhoto(photo: String?) {

        mEditor.putString(PHOTO, photo)
        mEditor.apply()
    }

    fun saveGender(gender: String) {
        mEditor.putString(GENDER, gender)
        mEditor.apply()
    }


    fun saveLanguage(langugage: String?) {

        mEditor.putString(LANGUAGE, langugage)
        mEditor.apply()
    }

    fun saveHeight(height: String) {
        mEditor.putString(HEIGHT, height)
        mEditor.apply()
    }


    fun saveHairColor(hairColor: String) {
        mEditor.putString(HAIR_COLOR, hairColor)
        mEditor.apply()
    }


    fun saveViews(height: String) {
        mEditor.putString(VIEWS, height)
        mEditor.apply()
    }


    fun saveLikes(hairColor: String) {
        mEditor.putString(LIKES, hairColor)
        mEditor.apply()
    }


    fun saveSmokes(smoke: String) {
        mEditor.putString(SMOKES, smoke)
        mEditor.apply()
    }

    fun saveDrinks(drinks: String) {
        mEditor.putString(DRINKS, drinks)
        mEditor.apply()
    }

    fun saveEducation(education: String) {
        mEditor.putString(EDUCATION, education)
        mEditor.apply()
    }


    fun saveBodyType(bodytype: String) {
        mEditor.putString(BODYTYPE, bodytype)
        mEditor.apply()
    }


    fun saveEthnicity(ethnicity: String) {
        mEditor.putString(ETHNICITY, ethnicity)
        mEditor.apply()
    }


    fun saveReligion(religion: String) {
        mEditor.putString(RELIGION, religion)
        mEditor.apply()
    }


    fun saveMyInterest(interest: String) {
        mEditor.putString(MYINTEREST, interest)
        mEditor.apply()
    }


    fun saveMyPet(pet: String) {
        mEditor.putString(PET, pet)
        mEditor.apply()
    }


    fun saveCountry(country: String) {
        mEditor.putString(COUNTRY, country)
        mEditor.apply()
    }

    fun saveBirthday(birthday: String) {
        mEditor.putString(BIRTHDAY, birthday)
        mEditor.apply()
    }

    fun savePhoneNumber(phoneNumber: String?) {
        mEditor.putString(PHONENUMBER, phoneNumber)
        mEditor.apply()
    }

    fun saveUserName(username: String) {
        mEditor.putString(USER_NAME, username)
        mEditor.apply()
    }

    fun savePhotoOne(photoUrl: String) {
        mEditor.putString(PHOTO1, photoUrl)
        mEditor.apply()
    }

    fun savePhotoTwo(photoUrl: String) {
        mEditor.putString(PHOTO2, photoUrl)
        mEditor.apply()
    }

    fun savePhotoThree(photoUrl: String) {
        mEditor.putString(PHOTO3, photoUrl)
        mEditor.apply()
    }

    fun savePhotoFour(photoUrl: String) {
        mEditor.putString(PHOTO4, photoUrl)
        mEditor.apply()
    }

    fun savePhotoFive(photoUrl: String) {
        mEditor.putString(PHOTO5, photoUrl)
        mEditor.apply()
    }

    fun savePhotoSix(photoUrl: String) {
        mEditor.putString(PHOTO6, photoUrl)
        mEditor.apply()
    }


    fun saveManageNotification(manageNotification: String) {
        mEditor.putString(MANAGENOTIFICATIONS, manageNotification)
        mEditor.apply()
    }

    fun saveVideoUrl(videoUrl: String) {
        mEditor.putString(VIDEO, videoUrl)
        mEditor.apply()
    }


    fun getVideoUrl(): String {
        return sharedPreferences.getString(VIDEO, "")!!
    }

    fun getName(): String {
        return sharedPreferences.getString(NAME, "")!!
    }



    fun getPhotoOne(): String {
        return sharedPreferences.getString(PHOTO1, "")!!
    }

    fun getPhotoTwo(): String {
        return sharedPreferences.getString(PHOTO2, "")!!
    }

    fun getPhotoThree(): String {
        return sharedPreferences.getString(PHOTO3, "")!!
    }

    fun getPhotoFour(): String {
        return sharedPreferences.getString(PHOTO4, "")!!
    }


    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, "")!!
    }

    fun getPhotoFive(): String {
        return sharedPreferences.getString(PHOTO5, "")!!
    }

    fun getPhotoSix(): String {
        return sharedPreferences.getString(PHOTO6, "")!!
    }


    fun getAboutMe(): String {
        return sharedPreferences.getString(ABOUTME, "")!!
    }

    fun getLocation(): String {
        return sharedPreferences.getString(LOCATION, "")!!
    }

    fun getManageNotifications(): String {
        return sharedPreferences.getString(MANAGENOTIFICATIONS, "")!!
    }

    fun getCountry(): String {
        return sharedPreferences.getString(COUNTRY, "")!!
    }

    fun getBirthday(): String {
        return sharedPreferences.getString(BIRTHDAY, "")!!
    }

    fun getPhoneNumber(): String {
        return sharedPreferences.getString(PHONENUMBER, "")!!
    }


    fun getMyPet(): String {
        return sharedPreferences.getString(PET, "")!!
    }

    fun getMyInterest(): String {
        return sharedPreferences.getString(MYINTEREST, "")!!
    }

    fun getReligion(): String {
        return sharedPreferences.getString(RELIGION, "")!!
    }

    fun getEthnicity(): String {
        return sharedPreferences.getString(ETHNICITY, "")!!
    }

    fun getBodyType(): String {
        return sharedPreferences.getString(BODYTYPE, "")!!
    }

    fun getEducation(): String {
        return sharedPreferences.getString(EDUCATION, "")!!
    }

    fun getDrinks(): String {
        return sharedPreferences.getString(DRINKS, "")!!
    }

    fun getSmokes(): String {
        return sharedPreferences.getString(SMOKES, "")!!
    }


    fun getViews(): String {
        return sharedPreferences.getString(VIEWS, "")!!
    }

    fun getLikes(): String {
        return sharedPreferences.getString(LIKES, "")!!
    }


    fun getLatitude(): String {
        return sharedPreferences.getString(LATITUDE, "")!!
    }

    fun getLongitude(): String {
        return sharedPreferences.getString(LONGITUDE, "")!!
    }


    fun getUserHairColor(): String {
        return sharedPreferences.getString(HAIR_COLOR, "")!!
    }

    fun getUserLanguage(): String {
        return sharedPreferences.getString(LANGUAGE, "")!!
    }

    fun getUserHeight(): String {
        return sharedPreferences.getString(HEIGHT, "")!!
    }

    fun getUserGender(): String {
        return sharedPreferences.getString(GENDER, "")!!
    }


    fun getUserFullName(): String {
        return sharedPreferences.getString(NAME, "")!!
    }

    fun getUserChurchName(): String {
        return sharedPreferences.getString(CHURCH, "")!!

    }

    fun getUserEmail(): String {
        return sharedPreferences.getString(USER_Email, "")!!

    }

    fun getUserMinistry(): String {
        return sharedPreferences.getString(MINISTRY, "")!!
    }

    fun getUserDiet(): String {
        return sharedPreferences.getString(DIET, "")!!
    }


    fun getRole(): String {
        return sharedPreferences.getString(ROLE, "")!!
    }

    fun getDeviceToken(): String {
        return sharedPreferences.getString(DEVICE_TOKEN, "")!!

    }

    fun getUserREf(): String {
        return sharedPreferences.getString(USER_REF, "")!!
    }

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID, "")!!
    }

    fun getUserPhoto(): String {
        return sharedPreferences.getString(PHOTO, "")!!
    }

}