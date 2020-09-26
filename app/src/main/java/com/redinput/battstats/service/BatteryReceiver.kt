package com.redinput.battstats.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.redinput.battstats.isServiceRunning
import com.redinput.battstats.updateWidgets

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BATTERY_CHANGED == intent.action) {
            context.updateWidgets()

        } else if (Intent.ACTION_MY_PACKAGE_REPLACED == intent.action) {
            if (!context.isServiceRunning()) {
                val intentService = Intent(context, BatteryService::class.java)
                ContextCompat.startForegroundService(context, intentService)
            }
        }
    }

}
