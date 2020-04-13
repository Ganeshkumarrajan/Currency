package com.ganesh.currency.di

import com.ganesh.currency.data.HttpService
import com.ganesh.currency.utill.createRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by ganeshkumarraja on 4/9/20.
 */

val httpModule = module {
    single<HttpService> {
        createRetrofit(androidContext()).create(HttpService::class.java)
    }
}
