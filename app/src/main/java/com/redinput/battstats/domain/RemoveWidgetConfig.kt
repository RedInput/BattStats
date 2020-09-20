package com.redinput.battstats.domain

import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.helpers.UseCase
import kotlinx.coroutines.CoroutineScope

class RemoveWidgetConfig(scope: CoroutineScope, private val repository: PreferencesRepository) : UseCase<UseCase.None, Int>(scope) {
    override suspend fun run(params: Int): Result {
        repository.removeWidgetInfo(params)
        return Result.Success(None())
    }
}