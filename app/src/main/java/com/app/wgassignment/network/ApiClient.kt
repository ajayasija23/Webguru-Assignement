package com.app.wgassignment.network

import android.util.Log

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private val client: OkHttpClient
        get() = OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
            request.method(original.method, original.body)
            chain.proceed(request.build())
        }
            .addInterceptor(provideHttpLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
                HttpLoggingInterceptor { message -> Log.d("Http Request => " ,message) }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return httpLoggingInterceptor
    }

}