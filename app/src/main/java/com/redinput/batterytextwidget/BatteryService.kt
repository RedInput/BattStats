package com.redinput.batterytextwidget

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat

class BatteryService : Service() {

    companion object {
        var isRunning = false
    }

    val NOTIFICATION_ID = 1004
    val NOTIFICATION_CHANNEL_ID = "foreground-service"

    lateinit var receiver: BatteryReceiver

    override fun onCreate() {
        super.onCreate()

        if (!isRunning) {
            showForegroundNotification()

            val receiverFilter = IntentFilter()
            receiverFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
            receiver = BatteryReceiver()
            registerReceiver(receiver, receiverFilter)

            isRunning = true
        }
    }

    @SuppressLint("NewApi")
    private fun showForegroundNotification() {
        val notificationIntent = Intent()
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_battery)
                .setContentTitle(getString(R.string.notification_title))
                .setContentIntent(pendingIntent)
        val notification = builder.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.service_channel_name), NotificationManager.IMPORTANCE_LOW)
            channel.description = getString(R.string.service_channel_description)
            channel.setShowBadge(false)
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_SECRET

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        stopForeground(true)

        isRunning = false

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}