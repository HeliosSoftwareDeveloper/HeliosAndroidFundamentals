/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification.NotificationActivity.Companion.NOTIFICATION_ACTION_CLICKED
import com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification.NotificationActivity.Companion.NOTIFICATION_ACTION_DELETE
import com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification.NotificationActivity.Companion.NOTIFICATION_ACTION_UPDATE

/**
 * Created by Ruel N. Grajo on 06/24/2019.
 *
 * BroadcastReceiver for notification receiver.
 */

class NotificationReceiver(notificationReceiverCallback : NotificationReceiverCallback) : BroadcastReceiver() {

    val mNotificationReceiverCallback = notificationReceiverCallback

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            NOTIFICATION_ACTION_UPDATE -> mNotificationReceiverCallback.onNotificationUpdateActionClicked()
            NOTIFICATION_ACTION_DELETE -> mNotificationReceiverCallback.onNotificationDeleted()
            NOTIFICATION_ACTION_CLICKED -> mNotificationReceiverCallback.onNotificationClicked()
        }
    }

    interface NotificationReceiverCallback {
        fun onNotificationUpdateActionClicked()
        fun onNotificationDeleted()
        fun onNotificationClicked()
    }
}
