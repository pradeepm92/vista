package com.health.vistacan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class FirebaseBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val activityIntent = Intent(context, MyFirebaseMessagingService::class.java)
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(activityIntent)
    }
}