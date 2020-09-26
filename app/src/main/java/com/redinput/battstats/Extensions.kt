package com.redinput.battstats

import android.app.ActivityManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.redinput.battstats.service.BatteryService
import com.redinput.battstats.ui.widget.BatteryWidget

@Suppress("DEPRECATION")
fun Context.isServiceRunning(): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (BatteryService::class.java.name == service.service.className) {
            return true
        }
    }
    return false
}

fun Context.updateWidgets() {
    val widgetManager = AppWidgetManager.getInstance(this)
    val widgetIds = widgetManager.getAppWidgetIds(ComponentName(this, BatteryWidget::class.java))

    val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, BatteryWidget::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
    sendBroadcast(intent)
}
