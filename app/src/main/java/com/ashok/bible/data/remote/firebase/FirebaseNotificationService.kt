package com.ashok.bible.data.remote.firebase

import android.app.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.PendingIntent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.app.NotificationManager
import android.content.Context
import java.net.URL
import android.app.NotificationChannel
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.ui.MainActivity

public class FirebaseNotificationService : FirebaseMessagingService() {
    private var numMessages = 0
    val FCM_PARAM = "picture"
    private val CHANNEL_NAME = "FCM"
    private val CHANNEL_DESC = "Firebase Cloud Messaging"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        notification?.let { sendNotification(it, data) }
    }

    private fun sendNotification(
        notification: RemoteMessage.Notification,
        data: Map<String, String>
    ) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("notification", "Summary Notification")
        intent.putExtra("notification_id", AppConstants.NOTIFICATION_ID)
        //intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        var body = notification.body
        var title = notification.title
        val picture = data[FCM_PARAM]
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                AppConstants.NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, body)
            type = "text/plain"
            if (picture != null) {
                val bmpUri = Uri.parse(picture);
                putExtra(Intent.EXTRA_STREAM, bmpUri)
                type = "image/*"
            }


        }


        val sharePendingIntent =
            PendingIntent.getActivity(
                this,
                AppConstants.NOTIFICATION_ID,
                sendIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        val notificationBuilder =
            NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("Bible")
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.drawable.ic_notification)
                .addAction(R.drawable.ic_share, "SHARE", sharePendingIntent)


        try {
            if (picture != null && "" != picture) {
                val url = URL(picture)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(
                        notification.body
                    )
                )
            }
        } catch (e: Exception) {
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.notification_channel_id),
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESC
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}