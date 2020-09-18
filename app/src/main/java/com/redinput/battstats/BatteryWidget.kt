package com.redinput.battstats

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
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
            prefs.removeWidgetBackground(appWidgetId)
            prefs.removeWidgetBehaviour(appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        val intentService = Intent(context, BatteryService::class.java)
        ContextCompat.startForegroundService(context, intentService)
    }

    override fun onDisabled(context: Context) {
        context.stopService(Intent(context, BatteryService::class.java))
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.battery_widget)

            val level = prefs.batteryLevel
            val status = prefs.batteryStatus

            if (level in 1..100) {
                val selection = prefs.getWidgetStyle(appWidgetId)
                val toWrite = when (selection) {
                    PreferenceHelper.WidgetStyle.TEXT -> {
                        val formatter = RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT)
                        formatter.format(level)
                    }
                    PreferenceHelper.WidgetStyle.NUMBER -> level.toString()
                }
                views.setTextViewText(R.id.appwidget_text, toWrite)

                val backgroundEnabled = prefs.getWidgetBackground(appWidgetId)
                if (backgroundEnabled) {
                    views.setViewVisibility(R.id.appwidget_background, View.VISIBLE)
                } else {
                    views.setViewVisibility(R.id.appwidget_background, View.INVISIBLE)
                }

                val behaviourClick = prefs.getWidgetBehaviour(appWidgetId)
                if (behaviourClick) {
                    val pIntent = PendingIntent.getActivity(context, appWidgetId.hashCode(),
                            Intent(Intent.ACTION_POWER_USAGE_SUMMARY), PendingIntent.FLAG_UPDATE_CURRENT)
                    views.setOnClickPendingIntent(R.id.appwidget_background, pIntent)
                } else {
                    views.setOnClickPendingIntent(R.id.appwidget_background, null)
                }
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

}
