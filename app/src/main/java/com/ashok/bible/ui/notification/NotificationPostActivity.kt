package com.ashok.bible.ui.notification

import android.net.Uri
import android.view.View
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.databinding.ActivityNotificationPostBinding
import com.ashok.bible.ui.model.NotificationMsgModel
import com.lakki.kotlinlearning.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_notification_post.*
import kotlinx.android.synthetic.main.activity_notification_post.view.*


class NotificationPostActivity : BaseActivity<NotificationViewModel, ActivityNotificationPostBinding>() {

    var imageUri: Uri? = null

    override fun getViewModel(): Class<NotificationViewModel> {
        return NotificationViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_notification_post
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.submit_btn->{
                val title = binding.editTitle.text.toString()
                val desc = binding.editComment.text.toString()
                val img = binding.editImage.text.toString()
                val notificationMsg = NotificationMsgModel()
                val notification = notificationMsg.notification;
                notification.title = title
                notification.body = desc
                val data = notificationMsg.data
                data.picture = img
                viewModel.pushNotifications(notificationMsg)

            }
        }
    }

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.handlers = this
        with(viewModel) {
            createPost.observe(this@NotificationPostActivity, Observer {
                dismissDialog()
                if (it != null) {
                    showSnackbar("Sent Notification Successfully.")
                    finish()
                }
            })

            error.observe(this@NotificationPostActivity, Observer {
                dismissDialog()
                if (it != null) {
                    showSnackbar(it.getErrorMessage())
                }
            })
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
