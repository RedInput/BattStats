package com.redinput.batterytextwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.*
import android.os.BatteryManager
import android.widget.RemoteViews
import com.ibm.icu.text.RuleBasedNumberFormat
import java.util.*

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
        val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
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
            val views = RemoteViews(context.packageName, R.layout.battery_widget)

            val selection = prefs.getWidgetStyle(appWidgetId)

            var level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val maxLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level = (level * 100f / maxLevel).toInt()

            val toWrite = when (selection) {
                PreferenceHelper.WidgetStyle.TEXT -> {
                    val formatter = RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT)
                    formatter.format(level)
                }
                PreferenceHelper.WidgetStyle.NUMBER -> level.toString()
            }

            views.setTextViewText(R.id.appwidget_text, toWrite)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
