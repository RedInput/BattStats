package com.redinput.battstats.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationIntent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            notificationIntent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            notificationIntent.putExtra("app_package", packageName)
            notificationIntent.putExtra("app_uid", applicationInfo.uid)
        } else {
            notificationIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            notificationIntent.addCategory(Intent.CATEGORY_DEFAULT)
            notificationIntent.data = Uri.parse("package:$packageName")
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_battery)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text_short))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.notification_text_long)))
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