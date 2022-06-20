package com.ashok.bible.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.databinding.ActivitySplashBinding
import com.ashok.bible.utils.DialogBuilder
import com.ashok.bible.utils.DialogListenerForLanguage
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity<ViewModel, ActivitySplashBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun init() {
        val isFirstTime = SharedPrefUtils.isFirstTime(pref)
        if (isFirstTime){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        select_language.setOnClickListener {
            DialogBuilder.showLanguage(
                this,
                object : DialogListenerForLanguage {
                    override fun language(id: String) {
                        SharedPrefUtils.setLanguage(pref, id)
                        SharedPrefUtils.saveFirstTime(pref)
                        Utils.copyAttachedDatabase(this@SplashActivity, id)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }

                })
        }
    }

    override fun getViewModel(): Class<ViewModel> {
        return ViewModel::class.java
    }
}
