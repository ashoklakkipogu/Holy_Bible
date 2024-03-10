package com.ashok.myapplication.data

object AppConstants {
    const val BASE_URL = "https://bible-8bdba.firebaseio.com/bible/"
    const val BASE_URL_NOTIFICATION = "https://fcm.googleapis.com/fcm/"
    const val FIRE_BASE_AUTH_KEY = "key=AAAAYtHj-wg:APA91bE7Cf4H4rT1GfUa6hjm6oLlZ20x7QfJltzpB06x0gXAzdzhJLe0VAZq2POc-9Kv1_ZX4OtGj3fYX4DmRdM1bUrpIlDm3Sw__NiDELD_o0GOpn4Ra2UE-FcN_mwsBcCCa5OEw2S_"
    const val CONNECT_TIMEOUT: Long = 60000
    const val READ_TIMEOUT: Long = 60000
    const val WRITE_TIMEOUT: Long = 60000

    const val SHARED_PREF: String = "BiblePref"

    const val DATE_FORMAT_dd_MM_YYYY = "dd-MM-YYYY"
    const val DATE_FORMAT_DD_MM_YYYY = "DD-MM-YYYY"
    const val DATE_FORMAT_dd_MM_yyyy_hh_mm_a = "dd-MM-yyyy hh:mm a"

    const val DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    const val EMAIL: String = "lakkideveloper@gmail.com"
    const val SUBJECT: String = "Holy Bible"
    const val NO_DATA_FOUND: String = "No data found, Coming soon!!"

    val LANGUAGES:List<String> = listOf("English", "Telugu", "Tamil", "Hindi", "Kannada")

    const val SUBSCRIBE_TO_TOPIC: String = "bible"
    const val NOTIFICATION_ID:Int = 101
}