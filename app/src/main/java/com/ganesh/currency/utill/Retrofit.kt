package com.ganesh.currency.utill

import android.content.Context
import com.ganesh.currency.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ganeshkumarraja on 4/1/20.
 */

fun createOkHttp(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(InternetConnectionInterceptor(context))
        .build()
}

fun createRetrofit(context: Context): Retrofit {
    return Retrofit.Builder()
        .client(createOkHttp(context))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.URL)
        .build()
}

class InternetConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Internet.isAvailable(context)) throw Exception(Internet.errorMessage)

        return chain.proceed(chain.request())
    }
}
