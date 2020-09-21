package com.redinput.battstats.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result

    operator fun invoke(params: Params, onResult: (Result) -> Unit = {}) {
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.async { run(params) }
        scope.launch(Dispatchers.Main) { onResult(job.await()) }
    }

    class None

    sealed class Result {
        data class Success<Type>(val data: Type) : Result()
        data class Error(val error: Throwable) : Result()
    }
}