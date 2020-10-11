package com.redinput.battstats

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlinx.serialization.Serializable

class Widget {

    @Serializable
    data class Config(
        var id: Int = AppWidgetManager.INVALID_APPWIDGET_ID,

        var displayStyle: DisplayStyle = DisplayStyle.TEXT,
        var textCaps: Boolean = false,
        var clickAction: ActionType = ActionType.BATTERY,

        @ColorInt var baseTextColor: Int = 0,
        var baseBackgroundEnabled: Boolean = true,
        @ColorInt var baseBackgroundColor: Int = 0,

        var lowEnabled: Boolean = false,
        @ColorInt var lowTextColor: Int = 0,
        var lowBackgroundEnabled: Boolean = true,
        @ColorInt var lowBackgroundColor: Int = 0,

        var chargingEnabled: Boolean = false,
        @ColorInt var chargingTextColor: Int = 0,
        var chargingBackgroundEnabled: Boolean = true,
        @ColorInt var chargingBackgroundColor: Int = 0,

        var fullEnabled: Boolean = false,
        @ColorInt var fullTextColor: Int = 0,
        var fullBackgroundEnabled: Boolean = true,
        @ColorInt var fullBackgroundColor: Int = 0,
    ) {
        constructor(context: Context) : this(
            baseTextColor = ContextCompat.getColor(context, R.color.default_base_text_color),
            baseBackgroundColor = ContextCompat.getColor(context, R.color.default_base_bg_color),
            lowTextColor = ContextCompat.getColor(context, R.color.default_low_text_color),
            lowBackgroundColor = ContextCompat.getColor(context, R.color.default_low_bg_color),
            chargingTextColor = ContextCompat.getColor(context, R.color.default_charging_text_color),
            chargingBackgroundColor = ContextCompat.getColor(context, R.color.default_charging_bg_color),
            fullTextColor = ContextCompat.getColor(context, R.color.default_full_text_color),
            fullBackgroundColor = ContextCompat.getColor(context, R.color.default_full_bg_color),
        )
    }

    enum class DisplayStyle {
        TEXT,
        NUMBER
    }

    enum class ActionType {
        BATTERY,
        CONFIG,
        NONE
    }
}