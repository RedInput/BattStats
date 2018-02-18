package com.redinput.batterytextwidget

import android.app.Application

val prefs: PreferenceHelper by lazy {
    BatteryWidgetApplication.prefs!!
}

class BatteryWidgetApplication : Application() {

    companion object {
        var prefs: PreferenceHelper? = null
    }

    override fun onCreate() {
        prefs = PreferenceHelper(applicationContext)
        super.onCreate()
    }
}