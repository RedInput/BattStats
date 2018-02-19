package com.redinput.batterytextwidget

import android.content.Context
import android.preference.PreferenceManager

// Based in this blog post: http://blog.teamtreehouse.com/making-sharedpreferences-easy-with-kotlin

class PreferenceHelper(context: Context) {

    enum class WidgetStyle {
        TEXT,
        NUMBER
    }

    val BATTERY_LEVEL = "battery-level"
    val BATTERY_STATUS = "battery-status"

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveWidgetStyle(mAppWidgetId: Int, style: WidgetStyle) {
        sharedPreferences.edit().putInt("widget-style-$mAppWidgetId", style.ordinal).apply()
    }

    fun getWidgetStyle(mAppWidgetId: Int): WidgetStyle {
        val style = sharedPreferences.getInt("widget-style-$mAppWidgetId", WidgetStyle.TEXT.ordinal)
        return WidgetStyle.values()[style]
    }

    fun removeWidgetStyle(mAppWidgetId: Int) {
        sharedPreferences.edit().remove("widget-style-$mAppWidgetId").apply()
    }

    var batteryLevel: Int
        get() = sharedPreferences.getInt(BATTERY_LEVEL, -1)
        set(value) = sharedPreferences.edit().putInt(BATTERY_LEVEL, value).apply()

    var batteryStatus: Int
        get() = sharedPreferences.getInt(BATTERY_STATUS, -1)
        set(value) = sharedPreferences.edit().putInt(BATTERY_STATUS, value).apply()

}
