/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import com.heliossoftwaredeveloper.heliosandroidfundamentals.R
import kotlinx.android.synthetic.main.activity_notification.*
import android.content.Intent

/**
 * Created by Ruel N. Grajo on 06/24/2019.
 *
 * Notification Activity for notification features.
 */

class NotificationActivity : AppCompatActivity(), View.OnClickListener, NotificationReceiver.NotificationReceiverCallback {

    private lateinit var notificationManager : NotificationManager
    private lateinit var receiver : NotificationReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        receiver = NotificationReceiver(this)
        //register intent action to receiver
        registerReceiver(receiver, IntentFilter(NOTIFICATION_ACTION_UPDATE))
        registerReceiver(receiver, IntentFilter(NOTIFICATION_ACTION_DELETE))
        registerReceiver(receiver, IntentFilter(NOTIFICATION_ACTION_CLICKED))

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        setNotificationButtonState(true, false, false)
        btn_notification_update.setOnClickListener(this)
        btn_notification_notify.setOnClickListener(this)
        btn_notification_cancel.setOnClickListener(this)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_notification_update -> {
                updateNotification()
            }
            R.id.btn_notification_notify -> {
                sendNotification()
            }
            R.id.btn_notification_cancel -> {
                cancelNotification()
            }
        }
    }

    override fun onNotificationUpdateActionClicked() {
        updateNotification()
    }

    override fun onNotificationDeleted() {
        setNotificationButtonState(true, false, false)
    }

    override fun onNotificationClicked() {
        startActivity(newIntent(this))
        finish()
    }

    /**
     * Function to call/trigger the send notification
     **/
    fun sendNotification() {
        val updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, Intent(NOTIFICATION_ACTION_UPDATE), PendingIntent.FLAG_ONE_SHOT)

        val notificationBilder = getNotificationBuilder()
        notificationBilder.addAction(R.drawable.ic_action_update, resources.getString(R.string.notification_action_title_update),
                updatePendingIntent)
        notificationManager.notify(NOTIFICATION_ID, notificationBilder.build())
        setNotificationButtonState(false, true, true)
    }

    /**
     * Function to call/trigger the update notification
     **/
    fun updateNotification() {
        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.helios_1)
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder.setStyle(NotificationCompat.BigPictureStyle()
                     .bigPicture(androidImage)
                     .setBigContentTitle(resources.getString(R.string.notification_title_update)))
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(false, false, true)
    }

    /**
     * Function to call/trigger the cancel notification
     **/
    fun cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
        setNotificationButtonState(true, false, false)
    }

    fun setNotificationButtonState(isNotifyEnabled : Boolean, isUpdateEnabled : Boolean, isCancelEnabled : Boolean) {
        btn_notification_update.isEnabled = isUpdateEnabled
        btn_notification_notify.isEnabled = isNotifyEnabled
        btn_notification_cancel.isEnabled = isCancelEnabled
    }

    /**
     * Function to create notification channel for Version Oreo and above
     **/
    @TargetApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * Function to get notification.builder
     *
     * @return NotificationCompat.Builder
     **/
    private fun getNotificationBuilder() : NotificationCompat.Builder {
        //set the pending intent to start NotificationActivity when the user clicked the notification
        val notificationPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, Intent(NOTIFICATION_ACTION_CLICKED),0)

        val deleteNotificationPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, Intent(NOTIFICATION_ACTION_DELETE), 0)

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(resources.getString(R.string.notification_title))
                .setContentText(resources.getString(R.string.notification_message))
                .setContentIntent(notificationPendingIntent)
                .setDeleteIntent(deleteNotificationPendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(false)
                //for backward compatibility, android 7.1 and below we need to set the priority here. For 8.0 up it is already set on notification channel
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Setting the priority is not enough for 7.1 and below, we also need to set defaults.
                .setDefaults(NotificationCompat.DEFAULT_ALL)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "helios_notification_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Helios Notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Notification from Helios"
        const val NOTIFICATION_ACTION_UPDATE = "com.heliossoftwaredeveloper.heliosandroidfundamentals.ACTION_UPDATE_NOTIFICATION"
        const val NOTIFICATION_ACTION_DELETE = "com.heliossoftwaredeveloper.heliosandroidfundamentals.ACTION_DELETE_NOTIFICATION"
        const val NOTIFICATION_ACTION_CLICKED = "com.heliossoftwaredeveloper.heliosandroidfundamentals.ACTION_CLICKED_NOTIFICATION"
        private const val NOTIFICATION_ID = 0

        /**
         * Function to create intent instance of the activity
         *
         * @param context the context of the caller activity
         * @return created intent
         **/
        fun newIntent(context: Context) : Intent {
            return Intent(context, NotificationActivity::class.java)
        }
    }
}
