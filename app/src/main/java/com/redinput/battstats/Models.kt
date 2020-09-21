package com.redinput.battstats

import androidx.annotation.ColorInt
import com.squareup.moshi.JsonClass

class Widget {
    @JsonClass(generateAdapter = true)
    data class Config(
        val id: Int,
        val asText: Boolean,
        @ColorInt val textColor: Int,
        val showBackground: Boolean,
        @ColorInt val backgroundColor: Int,
        val actionType: ActionType
    )

    enum class ActionType {
        BATTERY,
        CONFIG,
        NONE
    }
}