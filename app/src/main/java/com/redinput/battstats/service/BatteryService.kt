package com.redinput.battstats.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.redinput.battstats.R

class BatteryService : Service() {

    private val receiver = BatteryReceiver()

    override fun onCreate() {
        super.onCreate()

        showForegroundNotification()

        val receiverFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, receiverFilter)
    }

    private fun showForegroundNotification() {
        createChannel()

        val notificationIntent = Intent()
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_battery)
            .setContentTitle(getString(R.string.notification_title))
            .setContentIntent(pendingIntent)
        val notification = builder.build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.service_channel_name), NotificationManager.IMPORTANCE_LOW)
            channel.setShowBadge(false)
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_SECRET

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        stopForeground(true)

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val NOTIFICATION_ID = 1004
        private const val NOTIFICATION_CHANNEL_ID = "foreground-service"
    }
}