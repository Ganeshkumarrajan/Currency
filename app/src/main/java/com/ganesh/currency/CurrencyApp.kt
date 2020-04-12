package com.ganesh.currency

import android.app.Application
import com.ganesh.currency.di.httpModule
import com.ganesh.currency.di.repoModule
import com.ganesh.currency.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by ganeshkumarraja on 4/9/20.
 */
class CurrencyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyApp)
            modules(listOf(httpModule, repoModule, viewModelModule))
        }
    }
}
