package com.ashok.bible.ui.feedback

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.databinding.ActivityFeedbackBinding
import com.ashok.bible.ui.admin.BannerPosActivity
import com.ashok.bible.ui.admin.QuotesPosActivity
import com.ashok.bible.ui.lyrics.LyricPostActivity
import com.ashok.bible.ui.notification.NotificationPostActivity
import com.ashok.bible.utils.SharedPrefUtils
import com.ashok.bible.utils.Utils
import com.lakki.kotlinlearning.view.base.BaseActivity


class FeedbackActivity : BaseActivity<ViewModel,ActivityFeedbackBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_feedback
    }

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.handlers = this
        val userName = SharedPrefUtils.getUserName(pref)
        binding.notificationGroup.visibility = View.GONE
        userName?.let {
            if (it == AppConstants.NOTIFICATION_PASSWORD){
                binding.notificationGroup.visibility = View.VISIBLE

            }else{
                binding.notificationGroup.visibility = View.GONE
            }
        }
    }
    fun onClick(view: View) {
        when (view.id) {
            R.id.share_txt -> {
                Utils.shareApp(this)
            }
            R.id.rate_us_txt -> {
                Utils.goToPlayStore(this)
            }
            R.id.contact_txt -> {
                Utils.sendMail(this)
            }
            R.id.notification_txt -> {
                startActivity(Intent(this, NotificationPostActivity::class.java))
            }
            R.id.lyric_txt -> {
                startActivity(Intent(this, LyricPostActivity::class.java))
            }
            R.id.banner_txt -> {
                startActivity(Intent(this, BannerPosActivity::class.java))
            }
            R.id.quotes_txt -> {
                startActivity(Intent(this, QuotesPosActivity::class.java))
            }
        }
    }
    override fun getViewModel(): Class<ViewModel> {
        return ViewModel::class.java
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
