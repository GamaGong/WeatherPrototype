package com.example.weatherprototype.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .url(url = "${original.url}&appid=28947a0b8a65cc9821c087c4ac3fab24")
        val request: Request = builder.build()
        return chain.proceed(request)
    }
}
