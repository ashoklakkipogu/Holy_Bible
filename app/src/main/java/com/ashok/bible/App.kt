package com.ashok.bible

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.ashok.bible.common.AppConstants
import com.ashok.bible.di.component.AppComponent
import com.ashok.bible.di.component.DaggerAppComponent
import com.ashok.bible.utils.GlideImageLoader
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.Utils
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import lv.chi.photopicker.ChiliPhotoPicker
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    private lateinit var appComponent: AppComponent
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        initComponent()
        initNotificationSubscribe()
        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "com.ashok.bible.fileprovider"
        )
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        val isChecked = SharedPrefUtils.isDayOrNight(pref)
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val deviceName = SharedPrefUtils.getDeviceName(pref)
        if (deviceName == null) {
            val deviceModel = "${android.os.Build.BRAND} - ${android.os.Build.MODEL}"
            val deviceId = android.os.Build.ID
            SharedPrefUtils.setDeviceName(pref, deviceModel)
            SharedPrefUtils.setMobileID(pref, deviceId)
        }


    }

    private fun appComponent(): AppComponent {
        return appComponent
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    private fun initNotificationSubscribe() {
        val isSubscribe = SharedPrefUtils.isSubscribedNotifications(pref)
        if (!isSubscribe) {
            FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.SUBSCRIBE_TO_TOPIC)
                .addOnSuccessListener {
                    //Toast.makeText(applicationContext, "Subscribed Notifications", Toast.LENGTH_LONG).show();
                    SharedPrefUtils.saveSubscribedNotifications(pref)
                }
        }
    }
}