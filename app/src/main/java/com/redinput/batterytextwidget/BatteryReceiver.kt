package com.redinput.batterytextwidget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {

            val rawLevel = intent.getIntExtra("level", -1)
            val scale = intent.getIntExtra("scale", -1)
            val status = intent.getIntExtra("status", -1)

            var level = -1
            if (rawLevel >= 0 && scale > 0) {
                level = rawLevel * 100 / scale
            }

            prefs.batteryLevel = level
            prefs.batteryStatus = status

            context.sendBroadcast(Intent(BatteryWidget.ACTION_UPDATE_WIDGETS))
        }
    }

}
