package com.redinput.battstats.service

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.redinput.battstats.ui.widget.BatteryWidget

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BATTERY_CHANGED == intent.action) {
            val intentUpdate = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, context, BatteryWidget::class.java)
            val widgetManager = AppWidgetManager.getInstance(context)
            val ids = widgetManager.getAppWidgetIds(ComponentName(context, BatteryWidget::class.java))
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            context.sendBroadcast(intentUpdate)

        } else if (Intent.ACTION_MY_PACKAGE_REPLACED == intent.action) {
            if (!BatteryService.isRunning(context)) {
                val intentService = Intent(context, BatteryService::class.java)
                ContextCompat.startForegroundService(context, intentService)
            }
        }
    }

}
