package com.redinput.batterytextwidget

import android.app.Application

val prefs: PreferenceHelper by lazy {
    BatteryWidgetApplication.prefs!!
}

val receiver: BatteryReceiver by lazy {
    BatteryWidgetApplication.receiver!!
}

class BatteryWidgetApplication : Application() {

    companion object {
        var prefs: PreferenceHelper? = null
        var receiver: BatteryReceiver? = null
    }

    override fun onCreate() {
        prefs = PreferenceHelper(applicationContext)
        receiver = BatteryReceiver()
        super.onCreate()
    }
}