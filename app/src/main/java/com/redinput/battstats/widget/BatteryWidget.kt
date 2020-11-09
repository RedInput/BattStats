package com.redinput.battstats.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.view.View
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.ibm.icu.text.RuleBasedNumberFormat
import com.redinput.battstats.R
import com.redinput.battstats.Widget
import com.redinput.battstats.Widget.DisplayStyle.NUMBER
import com.redinput.battstats.Widget.DisplayStyle.TEXT
import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.domain.LoadWidgetConfig
import com.redinput.battstats.domain.RemoveWidgetConfig
import com.redinput.battstats.helpers.UseCase
import com.redinput.battstats.isServiceRunning
import com.redinput.battstats.service.BatteryService
import java.util.*

class BatteryWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        if (!context.isServiceRunning()) {
            val intentService = Intent(context, BatteryService::class.java)
            ContextCompat.startForegroundService(context, intentService)
        }

        val prefRepository = PreferencesRepository.getInstance(context)
        val batteryIntent = context.applicationContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        for (appWidgetId in appWidgetIds) {
            LoadWidgetConfig(prefRepository).invoke(
                appWidgetId,
                onResult = {
                    if ((it is UseCase.Result.Success<*>)
                        && (it.data is Widget.Config)) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, batteryIntent, it.data)
                    }
                }
            )
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        val prefRepository = PreferencesRepository.getInstance(context)
        for (appWidgetId in appWidgetIds) {
            RemoveWidgetConfig(prefRepository).invoke(appWidgetId)
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
        private val formatter = RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT)

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            batteryIntent: Intent?,
            widgetConfig: Widget.Config
        ) {
            if (batteryIntent == null) return

            val rawLevel = batteryIntent.extras?.getInt(BatteryManager.EXTRA_LEVEL) ?: 0
            val maxLevel = batteryIntent.extras?.getInt(BatteryManager.EXTRA_SCALE) ?: 100
            val level = rawLevel * 100 / maxLevel

            val status = batteryIntent.extras?.getInt(BatteryManager.EXTRA_STATUS)
                ?: BatteryManager.BATTERY_STATUS_UNKNOWN

            val isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING)
            val isFull = (status == BatteryManager.BATTERY_STATUS_FULL)
            val isLow = ((status != BatteryManager.BATTERY_STATUS_CHARGING) && (status != BatteryManager.BATTERY_STATUS_FULL) && (level <= 15))

            val views = RemoteViews(context.packageName, R.layout.battery_widget)

            if (level in 0..100) {
                var showLevel = when (widgetConfig.displayStyle) {
                    TEXT -> formatter.format(level)
                    NUMBER -> level.toString()
                }
                if (widgetConfig.textCaps) {
                    showLevel = showLevel.toUpperCase(Locale.getDefault())
                }
                views.setTextViewText(R.id.appwidget_text, showLevel)


                if ((isFull) && (widgetConfig.fullEnabled)) {
                    views.setTextColor(R.id.appwidget_text, widgetConfig.fullTextColor)
                    if (widgetConfig.fullBackgroundEnabled) {
                        views.setViewVisibility(R.id.appwidget_background, View.VISIBLE)
                        views.setInt(R.id.appwidget_background, "setBackgroundColor", widgetConfig.fullBackgroundColor)
                    } else {
                        views.setViewVisibility(R.id.appwidget_background, View.INVISIBLE)
                    }

                } else if ((isCharging) && (widgetConfig.chargingEnabled)) {
                    views.setTextColor(R.id.appwidget_text, widgetConfig.chargingTextColor)
                    if (widgetConfig.chargingBackgroundEnabled) {
                        views.setViewVisibility(R.id.appwidget_background, View.VISIBLE)
                        views.setInt(R.id.appwidget_background, "setBackgroundColor", widgetConfig.chargingBackgroundColor)
                    } else {
                        views.setViewVisibility(R.id.appwidget_background, View.INVISIBLE)
                    }

                } else if ((isLow) && (widgetConfig.lowEnabled)) {
                    views.setTextColor(R.id.appwidget_text, widgetConfig.lowTextColor)
                    if (widgetConfig.lowBackgroundEnabled) {
                        views.setViewVisibility(R.id.appwidget_background, View.VISIBLE)
                        views.setInt(R.id.appwidget_background, "setBackgroundColor", widgetConfig.lowBackgroundColor)
                    } else {
                        views.setViewVisibility(R.id.appwidget_background, View.INVISIBLE)
                    }

                } else {
                    views.setTextColor(R.id.appwidget_text, widgetConfig.baseTextColor)
                    if (widgetConfig.baseBackgroundEnabled) {
                        views.setViewVisibility(R.id.appwidget_background, View.VISIBLE)
                        views.setInt(R.id.appwidget_background, "setBackgroundColor", widgetConfig.baseBackgroundColor)
                    } else {
                        views.setViewVisibility(R.id.appwidget_background, View.INVISIBLE)
                    }
                }


                var actionIntent: PendingIntent? = null
                when (widgetConfig.clickAction) {
                    Widget.ActionType.BATTERY -> {
                        val intent = Intent(Intent.ACTION_POWER_USAGE_SUMMARY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        actionIntent = PendingIntent.getActivity(context, appWidgetId.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    }
                    Widget.ActionType.CONFIG -> {
                        val appWidget = appWidgetManager.getAppWidgetInfo(appWidgetId)
                        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)
                        intent.component = appWidget.configure
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                        actionIntent = PendingIntent.getActivity(context, appWidgetId.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    }
                    Widget.ActionType.NONE -> {
                        actionIntent = null
                    }
                }
                views.setOnClickPendingIntent(R.id.appwidget_root, actionIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
