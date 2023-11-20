package com.ashok.myapplication.ui.utilities

import android.content.SharedPreferences

class SharedPrefUtils {
    companion object {
        const val DYA_NIGHT = "day/night"
        const val LANGUAGE = "language"
        const val FIRST_TIME = "firstTime"
        const val FAVORITE_MODEL = "favoriteModel"
        const val NOTE_MODEL = "noteModel"
        const val HIGHLIGHT_MODEL = "highlightModel"
        const val USER_NAME = "userName"
        const val ID = "ID"
        const val DEVICE_NAME = "deviceName"
        const val SUBSCRIBE_NOTIFICATION = "subscribeNotification"
        const val IS_SAVED_USER_NAME = "isSavedUserName"
        const val THEME_SIZE = "THEME_SIZE"
        const val LINE_SPACE = "LINE_SPACE"


        fun setUserName(pref: SharedPreferences, name: String) {
            val editor = pref.edit()
            editor.putString(USER_NAME, name)
            editor.apply()
        }

        fun getUserName(pref: SharedPreferences): String? {
            return pref.getString(USER_NAME, null)
        }

        fun setMobileID(pref: SharedPreferences, name: String) {
            val editor = pref.edit()
            editor.putString(ID, name)
            editor.apply()
        }

        fun getId(pref: SharedPreferences): String? {
            return pref.getString(ID, null)
        }

        fun setDeviceName(pref: SharedPreferences, name: String) {
            val editor = pref.edit()
            editor.putString(DEVICE_NAME, name)
            editor.apply()
        }

        fun getDeviceName(pref: SharedPreferences): String? {
            return pref.getString(DEVICE_NAME, null)
        }


        fun setDayOrNight(pref: SharedPreferences, isDay: Boolean) {
            val editor = pref.edit()
            editor.putBoolean(DYA_NIGHT, isDay)
            editor.apply()
        }

        fun setLanguage(pref: SharedPreferences, language: String) {
            val editor = pref.edit()
            editor.putString(LANGUAGE, language)
            editor.apply()
        }

        fun isDayOrNight(pref: SharedPreferences): Boolean {
            return pref.getBoolean(DYA_NIGHT, false)
        }

        fun getLanguage(pref: SharedPreferences): String? {
            return pref.getString(LANGUAGE, null)
        }

        fun saveFirstTime(pref: SharedPreferences) {
            val editor = pref.edit()
            editor.putBoolean(FIRST_TIME, true)
            editor.apply()
        }

        fun isFirstTime(pref: SharedPreferences): Boolean {
            return pref.getBoolean(FIRST_TIME, false)
        }

        fun saveHighLights(pref: SharedPreferences, str: String) {
            val editor = pref.edit()
            editor.putString(HIGHLIGHT_MODEL, str)
            editor.apply()
        }

        fun saveFavorite(pref: SharedPreferences, str: String) {
            val editor = pref.edit()
            editor.putString(FAVORITE_MODEL, str)
            editor.apply()
        }

        fun saveNote(pref: SharedPreferences, str: String) {
            val editor = pref.edit()
            editor.putString(NOTE_MODEL, str)
            editor.apply()
        }

        fun getData(pref: SharedPreferences, key: String): String? {
            return pref.getString(key, null)
        }

        fun removeData(pref: SharedPreferences, key: String) {
            pref.edit().remove(key).commit();
        }

        fun deleteAllSharePrefs(pref: SharedPreferences) {
            val editor = pref.edit()
            editor.clear()
            editor.apply()

        }

        fun saveSubscribedNotifications(pref: SharedPreferences) {
            val editor = pref.edit()
            editor.putBoolean(SUBSCRIBE_NOTIFICATION, true)
            editor.apply()
        }

        fun isSubscribedNotifications(pref: SharedPreferences): Boolean {
            return pref.getBoolean(SUBSCRIBE_NOTIFICATION, false)
        }

        fun setSavedUser(pref: SharedPreferences) {
            val editor = pref.edit()
            editor.putBoolean(IS_SAVED_USER_NAME, true)
            editor.apply()
        }

        fun isSavedUser(pref: SharedPreferences): Boolean {
            return pref.getBoolean(IS_SAVED_USER_NAME, false)
        }

        fun setTheme(pref: SharedPreferences, size: Int) {
            val editor = pref.edit()
            editor.putInt(THEME_SIZE, size)
            editor.apply()
        }

       /* fun getTheme(pref: SharedPreferences): Int {
            return pref.getInt(THEME_SIZE, TEXT_SIZE_1)
        }*/

        fun setLineSpace(pref: SharedPreferences, size: Int) {
            val editor = pref.edit()
            editor.putInt(LINE_SPACE, size)
            editor.apply()
        }

        /*fun getLineSpace(pref: SharedPreferences): Int {
            return pref.getInt(LINE_SPACE, TEXT_LINE_1)
        }*/

    }
}