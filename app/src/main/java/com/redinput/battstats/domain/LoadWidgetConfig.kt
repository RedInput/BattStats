package com.redinput.battstats.domain

import com.redinput.battstats.Widget
import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.helpers.UseCase
import kotlinx.coroutines.CoroutineScope

class LoadWidgetConfig(scope: CoroutineScope, private val repository: PreferencesRepository) : UseCase<Widget.Config, Int>(scope) {
    override suspend fun run(params: Int): Result {
        val config = repository.loadWidgetInfo(params)
        return Result.Success(config)
    }
}