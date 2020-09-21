package com.redinput.battstats.domain

import com.redinput.battstats.Widget
import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.helpers.UseCase

class LoadWidgetConfig(private val repository: PreferencesRepository) : UseCase<Widget.Config, Int>() {
    override suspend fun run(params: Int): Result {
        val config = repository.loadWidgetInfo(params)
        return Result.Success(config)
    }
}