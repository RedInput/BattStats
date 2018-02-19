package com.redinput.battstats

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

    val PREF_WIDGET_STYLE = "widget-style-"
    val PREF_WIDGET_BACKGROUND = "widget-background-"
    val PREF_WIDGET_BEHAVIOUR = "widget-behaviour-"

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveWidgetStyle(mAppWidgetId: Int, style: WidgetStyle) {
        sharedPreferences.edit().putInt(PREF_WIDGET_STYLE + mAppWidgetId, style.ordinal).apply()
    }

    fun getWidgetStyle(mAppWidgetId: Int): WidgetStyle {
        val style = sharedPreferences.getInt(PREF_WIDGET_STYLE + mAppWidgetId, WidgetStyle.TEXT.ordinal)
        return WidgetStyle.values()[style]
    }

    fun removeWidgetStyle(mAppWidgetId: Int) {
        sharedPreferences.edit().remove(PREF_WIDGET_STYLE + mAppWidgetId).apply()
    }

    fun saveWidgetBackground(mAppWidgetId: Int, enabled: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_WIDGET_BACKGROUND + mAppWidgetId, enabled).apply()
    }

    fun getWidgetBackground(mAppWidgetId: Int): Boolean {
        return sharedPreferences.getBoolean(PREF_WIDGET_BACKGROUND + mAppWidgetId, true)
    }

    fun removeWidgetBackground(mAppWidgetId: Int) {
        sharedPreferences.edit().remove(PREF_WIDGET_BACKGROUND + mAppWidgetId).apply()
    }

    fun saveWidgetBehaviour(mAppWidgetId: Int, enabled: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_WIDGET_BEHAVIOUR + mAppWidgetId, enabled).apply()
    }

    fun getWidgetBehaviour(mAppWidgetId: Int): Boolean {
        return sharedPreferences.getBoolean(PREF_WIDGET_BEHAVIOUR + mAppWidgetId, true)
    }

    fun removeWidgetBehaviour(mAppWidgetId: Int) {
        sharedPreferences.edit().remove(PREF_WIDGET_BEHAVIOUR + mAppWidgetId).apply()
    }

    var batteryLevel: Int
        get() = sharedPreferences.getInt(BATTERY_LEVEL, -1)
        set(value) = sharedPreferences.edit().putInt(BATTERY_LEVEL, value).apply()

    var batteryStatus: Int
        get() = sharedPreferences.getInt(BATTERY_STATUS, -1)
        set(value) = sharedPreferences.edit().putInt(BATTERY_STATUS, value).apply()

}
