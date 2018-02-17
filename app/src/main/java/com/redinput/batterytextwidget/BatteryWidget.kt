package com.redinput.batterytextwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.RemoteViews

class BatteryWidget : AppWidgetProvider() {

    public override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    public override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {

        }
    }

    public override fun onEnabled(context: Context) {
        val batteryReceiver = BatteryWidget()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, intentFilter)

    }

    public override fun onDisabled(context: Context) {
        val batteryReceiver = BatteryWidget()
        context.unregisterReceiver(batteryReceiver)
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.getPackageName(), R.layout.battery_widget)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
