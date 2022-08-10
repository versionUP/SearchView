package com.example.prashanth.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientObj {


    /**
     * to enable logging
     */
    private var logging = true


    /**
     * lazy initialization of OkHttpClient
     * */

    private val okHttpInstanceDelegate = lazy {

        OkHttpClient.Builder().apply {
            if (logging) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.callTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .build()

    }

    /**
     * singleton instance to access OkHttpClient
     * */
    val instance by okHttpInstanceDelegate
}
