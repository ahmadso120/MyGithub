package com.sopian.mygithub.core.data.source.remote.network

import com.sopian.mygithub.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AccessTokenInterceptor @Inject constructor (): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.TOKEN

        val authenticatedRequestBody = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(authenticatedRequestBody)
    }
}