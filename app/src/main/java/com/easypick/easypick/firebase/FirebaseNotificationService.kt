package com.easypick.easypick.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.Principal
import com.easypick.easypick.R
import com.easypick.easypick.preferences.MyPreferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService: FirebaseMessagingService() {
    private val CHANNEL_ID = "ORDER_EVENTS"

    override fun onNewToken(newToken: String) {
        MyPreferences.setFirebaseToken(this, newToken)
        DatabaseAPI().updateToken(newToken)
        super.onNewToken(newToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            showNotification(remoteMessage.notification!!, remoteMessage.data)
        }
    }

    private fun showNotification(
        notification: RemoteMessage.Notification,
        data: MutableMap<String, String>
    ) {
        val intent = Intent(this, Principal::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("fragment", "OrdenFragment");
        intent.putExtra("ordenId", data["ordenId"])

        val requestCode = 0

        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}