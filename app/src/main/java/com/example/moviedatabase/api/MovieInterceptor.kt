package com.example.moviedatabase.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

private const val API_KEY: String = "0135a8ca1095d143aa2e758506c9cb02"

class MovieInterceptor @Inject constructor(): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest: Request = chain.request()
        val newUrl: HttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
