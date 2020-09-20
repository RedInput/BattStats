package com.redinput.battstats.domain

import com.redinput.battstats.Widget
import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.helpers.UseCase
import kotlinx.coroutines.CoroutineScope

class SaveWidgetConfig(scope: CoroutineScope, private val repository: PreferencesRepository) : UseCase<UseCase.None, Widget.Config>(scope) {
    override suspend fun run(params: Widget.Config): Result {
        repository.saveWidgetInfo(params)
        return Result.Success(None())
    }
}