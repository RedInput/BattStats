package com.redinput.battstats.data

import com.redinput.battstats.Widget

interface PreferencesSource {
    fun saveWidgetInfo(info: Widget.Config)
    fun loadWidgetInfo(id: Int): Widget.Config?
    fun removeWidgetInfo(id: Int)
}