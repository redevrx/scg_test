package com.redev.scg_test.core.network

import com.redev.scg_test.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUrl = chain.request().url
        val newUrl = currentUrl.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }
}