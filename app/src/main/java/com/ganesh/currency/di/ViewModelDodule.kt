package com.ganesh.currency.di

import com.ganesh.currency.feature.currency_list.CurrencyViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by ganeshkumarraja on 4/9/20.
 */

val viewModelModule = module {
    viewModel {
        CurrencyViewModel(get(), get())
    }
}
