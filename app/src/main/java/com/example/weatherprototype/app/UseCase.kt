package com.example.weatherprototype.app

import kotlinx.coroutines.flow.Flow

interface UseCase<in ARG, out RESULT> {
    suspend fun run(arg: ARG): RESULT
}

interface FlowUseCase<in ARG, out RESULT> {
    fun getFlow(arg: ARG): Flow<RESULT>
}

suspend fun <ARG, RESULT> UseCase<ARG, RESULT>.runWithResult(
    arg: ARG,
    handleResult: (RESULT) -> Unit,
    handleError: (Throwable) -> Unit
) {
    try {
        handleResult(this.run(arg))
    } catch (ex: Throwable) {
        handleError(ex)
    }
}