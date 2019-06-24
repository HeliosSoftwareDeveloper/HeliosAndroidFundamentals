/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Ruel N. Grajo on 06/24/2019.
 *
 * BroadcastReceiver for notification receiver.
 */

class NotificationReceiver(notificationReceiverCallback : NotificationReceiverCallback) : BroadcastReceiver() {

    val mNotificationReceiverCallback = notificationReceiverCallback

    override fun onReceive(context: Context, intent: Intent) {
        mNotificationReceiverCallback.onNotificationActionClicked(intent)
    }

    interface NotificationReceiverCallback {
        fun onNotificationActionClicked(intent: Intent)
    }
}
