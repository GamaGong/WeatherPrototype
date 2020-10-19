package com.example.weatherprototype.common

interface UseCase<in ARG, out RESULT> {
    suspend fun run(arg: ARG): RESULT
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