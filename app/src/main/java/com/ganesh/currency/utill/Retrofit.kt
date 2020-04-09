package com.ganesh.currency.utill

import com.ganesh.currency.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ganeshkumarraja on 4/1/20.
 */


fun createOkHttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}

fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .client(createOkHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.URL)
        .build()
}



