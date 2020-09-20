package com.redinput.battstats

import androidx.annotation.ColorInt
import com.squareup.moshi.JsonClass

class Widget {
    @JsonClass(generateAdapter = true)
    data class Config(
        val id: Int,
        @ColorInt val textColor: Int,
        @ColorInt val backgroundColor: Int
    )
}