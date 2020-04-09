package com.ganesh.currency.di

import com.ganesh.currency.data.Service
import com.ganesh.currency.utill.createRetrofit
import org.koin.dsl.module

/**
 * Created by ganeshkumarraja on 4/9/20.
 */

val httpModule = module {
    single<Service> {
        createRetrofit().create(Service::class.java)
    }
}