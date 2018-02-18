package com.redinput.batterytextwidget

import android.content.Context
import android.preference.PreferenceManager

// Loosely based in this blog post: http://blog.teamtreehouse.com/making-sharedpreferences-easy-with-kotlin

class PreferenceHelper(context: Context) {

    enum class WidgetStyle {
        TEXT,
        NUMBER
    }

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveWidgetStyle(mAppWidgetId: Int, style: WidgetStyle) {
        sharedPreferences.edit().putInt("widget-style-$mAppWidgetId", style.ordinal).apply()
    }

    fun getWidgetStyle(mAppWidgetId: Int): WidgetStyle {
        val style = sharedPreferences.getInt("widget-style-$mAppWidgetId", WidgetStyle.TEXT.ordinal)
        return WidgetStyle.values()[style]
    }

}