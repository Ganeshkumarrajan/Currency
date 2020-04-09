package com.ganesh.currency.di


import com.ganesh.currency.data.Repository
import com.ganesh.currency.data.RepositoryImpl

import com.ganesh.currency.feature.currency_list.RateRespose

import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by ganeshkumarraja on 4/9/20.
 */


val repoModule = module {
    single<Repository<RateRespose>> {
        RepositoryImpl(get(), Schedulers.newThread(), AndroidSchedulers.mainThread())
    }
}