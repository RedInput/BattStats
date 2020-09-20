package com.redinput.battstats

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import androidx.core.content.ContextCompat

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {

            val rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

            var level = -1
            if (rawLevel >= 0 && scale > 0) {
                level = rawLevel * 100 / scale
            }

            prefs.batteryLevel = level
            prefs.batteryStatus = status

            // Launch intent to update all appwidgets
            val intentUpdate = Intent(context, BatteryWidget::class.java)
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val widgetManager = AppWidgetManager.getInstance(context)
            val ids = widgetManager.getAppWidgetIds(ComponentName(context, BatteryWidget::class.java))
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            context.sendBroadcast(intentUpdate)

        } else if (Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)) {
            if (!BatteryService.isRunning(context)) {
                val intentService = Intent(context, BatteryService::class.java)
                ContextCompat.startForegroundService(context, intentService)
            }
        }
    }

}