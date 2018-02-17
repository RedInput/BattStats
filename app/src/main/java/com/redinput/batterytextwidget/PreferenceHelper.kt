package com.redinput.batterytextwidget

import android.content.Context
import android.preference.PreferenceManager

// Class based in this blog post: http://blog.teamtreehouse.com/making-sharedpreferences-easy-with-kotlin

class PreferenceHelper(context: Context) {

    enum class ShowStyle(val value: Int) {
        TEXT(0),
        NUMBER(1)
    }

    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    val SHOW_STYLE = "show_style"

    var bgcolor
        get() = prefs.getInt(SHOW_STYLE, ShowStyle.TEXT.value)
        set(value) = prefs.edit().putInt(SHOW_STYLE, value).apply()

}