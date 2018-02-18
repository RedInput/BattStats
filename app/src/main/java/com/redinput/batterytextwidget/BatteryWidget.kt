package com.redinput.batterytextwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.*
import android.os.BatteryManager
import android.widget.RemoteViews

class BatteryWidget : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val action = intent.action
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, BatteryWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            if (appWidgetIds.isNotEmpty()) {
                onUpdate(context, appWidgetManager, appWidgetIds)
            }
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, batteryStatus)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {

        }
    }

    override fun onEnabled(context: Context) {
        batteryReceiver = BatteryWidget()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.applicationContext.registerReceiver(batteryReceiver, intentFilter)
    }

    override fun onDisabled(context: Context) {
        context.applicationContext.unregisterReceiver(batteryReceiver)
    }

    companion object {

        lateinit var batteryReceiver: BroadcastReceiver

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, batteryStatus: Intent) {
            val views = RemoteViews(context.getPackageName(), R.layout.battery_widget)

            val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val selection = prefs.getWidgetStyle(appWidgetId)

            views.setTextViewText(R.id.appwidget_text, level.toString() + " - " + selection.name)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
