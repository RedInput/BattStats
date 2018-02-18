package com.redinput.batterytextwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
            // TODO remove widget prefs
        }
    }

    override fun onEnabled(context: Context) {
        context.applicationContext.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onDisabled(context: Context) {
        context.applicationContext.unregisterReceiver(receiver)
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
