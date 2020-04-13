package com.ganesh.currency.di

import com.ganesh.currency.data.Repository
import com.ganesh.currency.data.RepositoryImpl
import com.ganesh.currency.feature.currency_list.RateRespose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

/**
 * Created by ganeshkumarraja on 4/9/20.
 */
val repoModule = module {
    single<Repository<RateRespose>> {
        RepositoryImpl(get(), Schedulers.newThread(), AndroidSchedulers.mainThread())
    }

    single {
        Country
    }
}

object Country {
    val currencyMap: HashMap<String, String> = HashMap()

    init {
        addName()
    }

    private fun addName() {
        currencyMap["EUR"] = "Germany"
        currencyMap["AUD"] = "Australian Dollar"
        currencyMap["BGN"] = "Bulgarian Lev"
        currencyMap["BRL"] = "Brazilian Real"
        currencyMap["CAD"] = "Canadian Dollar"
        currencyMap["CHF"] = "Swiss Franc"
        currencyMap["CNY"] = "Renminbi"
        currencyMap["CZK"] = "Czech Koruna"
        currencyMap["DKK"] = "Danish Krone"
        currencyMap["GBP"] = "Pound Sterling"
        currencyMap["HKD"] = "Hong Kong Dollar"
        currencyMap["HRK"] = "Croatian Kuna"
        currencyMap["HUF"] = "Hungarian Forint"
        currencyMap["IDR"] = "Indonesian Rupiah"
        currencyMap["ILS"] = "Israeli New"
        currencyMap["INR"] = "Indian Rupee"
        currencyMap["ISK"] = "Iceland"
        currencyMap["JPY"] = "Japanese Yen"
        currencyMap["KRW"] = "South Korean Won"
        currencyMap["MXN"] = "Mexican Peso"
        currencyMap["MYR"] = "Malaysian Ringgit"
        currencyMap["NOK"] = "Norwegian Krone"
        currencyMap["NZD"] = "New Zealand Dollar"
        currencyMap["PHP"] = "Philippine Peso"
        currencyMap["PLN"] = "Polish Złoty"
        currencyMap["RON"] = "Romanian Leu"
        currencyMap["RUB"] = "Russian ruble"
        currencyMap["SEK"] = "Swedish Krona"
        currencyMap["SGD"] = "Singapore Dollar"
        currencyMap["THB"] = "Thai baht"
        currencyMap["TRY"] = "Turkish Lira"
        currencyMap["USD"] = "United States Dollar"
        currencyMap["ZAR"] = "South African rand"
    }
}
