package com.health.vistacan


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService: FirebaseMessagingService(){

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "com.health.vistacan"
    }
    override fun onNewToken(token: String) {
        Log.e("PUSH_TOKEN", " firebase token: $token")
    }
    override fun onMessageReceived(message: RemoteMessage) {
        val click_action: String? = message.data?.get("click_action")
        val fragmentid: String? =message.data.get("Fragment_Id")
        val notification: String? =message.data.get("notification")
        val title: String? =message.data.get("title")
        val body: String? =message.data.get("body")

        if (message.data!= null) {
            generateNotification(title!!,body!!,click_action,fragmentid)
        }

    }



    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.health.vistacan", R.layout.notificationfirebase)
        remoteView.setTextViewText(R.id.titlenof, title)
        remoteView.setTextViewText(R.id.messagenof, message)
        remoteView.setImageViewResource(R.id.imagenof, R.drawable.logo)
        return remoteView
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun generateNotification(
        title: String,
        message: String,
        click_action: String?,
        fragmentid: String?
    ) {
        val intent=Intent(click_action)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("notification",true)
        intent.putExtra("fragmentId",fragmentid)

            Log.e("fragmentid_api", fragmentid!! )
        Log.e("fragmentid_string", fragmentid.toString() )


        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.logo)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
        builder.setCustomContentView(getRemoteView(title, message))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }


}
