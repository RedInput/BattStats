package com.redinput.battstats.data

import com.redinput.battstats.Widget

interface PreferencesSource {
    fun saveWidgetInfo(info: Widget.Config)
    fun loadWidgetInfo(widgetId: Int): Widget.Config?
    fun removeWidgetInfo(widgetId: Int)
}