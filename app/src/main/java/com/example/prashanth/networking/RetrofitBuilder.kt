package com.example.prashanth.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {


    /**
    * function to get Implementation of Service class
    * */
    fun <T> getProviderClass(
        baseURL: String, service: Class<T>,
        client: OkHttpClient = OkHttpClientObj.instance,
        converterFactory: Converter.Factory = GsonConverterFactory.create(
            GsonBuilder().create()
        )
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
        return retrofit.create(service)

    }
}


