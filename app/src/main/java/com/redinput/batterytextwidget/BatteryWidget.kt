package com.redinput.batterytextwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.ibm.icu.text.RuleBasedNumberFormat
import java.util.*

class BatteryWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            prefs.removeWidgetStyle(appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, BatteryService::class.java))
        } else {
            context.startService(Intent(context, BatteryService::class.java))
        }
    }

    override fun onDisabled(context: Context) {
        context.stopService(Intent(context, BatteryService::class.java))
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.battery_widget)

            val selection = prefs.getWidgetStyle(appWidgetId)
            val level = prefs.batteryLevel
            val status = prefs.batteryStatus

            if (level >= 0) {
                val toWrite = when (selection) {
                    PreferenceHelper.WidgetStyle.TEXT -> {
                        val formatter = RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT)
                        formatter.format(level)
                    }
                    PreferenceHelper.WidgetStyle.NUMBER -> level.toString()
                }

                views.setTextViewText(R.id.appwidget_text, toWrite)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
