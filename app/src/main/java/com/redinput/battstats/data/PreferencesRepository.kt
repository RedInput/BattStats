package com.redinput.battstats.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.redinput.battstats.Widget
import com.redinput.battstats.helpers.SingletonHolder
import com.squareup.moshi.Moshi

class PreferencesRepository private constructor(context: Context) : PreferencesSource {

    companion object : SingletonHolder<PreferencesRepository, Context>(::PreferencesRepository) {
        private const val KEY_WIDGET_CONFIG_PREFIX = "widget-"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val moshi = Moshi.Builder().build()
    private val moshiWidget = moshi.adapter(Widget.Config::class.java)

    override fun saveWidgetInfo(info: Widget.Config) {
        val json = moshiWidget.toJson(info)
        preferences.edit().putString(KEY_WIDGET_CONFIG_PREFIX + info.id, json).apply()
    }

    override fun loadWidgetInfo(id: Int): Widget.Config? {
        val json = preferences.getString(KEY_WIDGET_CONFIG_PREFIX + id, null)
        if (json != null) {
            return moshiWidget.fromJson(json)
        }
        return null
    }

    override fun removeWidgetInfo(id: Int) {
        preferences.edit().remove(KEY_WIDGET_CONFIG_PREFIX + id).apply()
    }
}