package com.ganesh.currency.feature.currency_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganesh.currency.data.Repository


/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyViewModel(private val repository: Repository<RateRespose>) : ViewModel() {

    private var rateLiveData: MutableLiveData<List<RateModel>> = MutableLiveData()
    val rate: LiveData<List<RateModel>> = rateLiveData

    private var errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val errMessage: LiveData<String> = errorMessageLiveData

    private var baseValue: Float = 100F
    private val shynRateUpdate: Any = Object()

    init {
        ratesFromRepo()
    }

    private fun ratesFromRepo() {
        repository.doRequest("EUR", {
            updateLatestRates(it)
        }, {
            errorMessageLiveData.value = it.message
        }
        )
    }

    private fun updateLatestRates(latestRates: RateRespose) {

        val newCurrencyRates: MutableList<RateModel> = mutableListOf()

        synchronized(shynRateUpdate) {

            newCurrencyRates.add(RateModel(latestRates.baseCurrency, 1.0F, baseValue))

            latestRates.rates?.forEach { (currency, rate) ->
                newCurrencyRates.add(RateModel(currency, rate, rate * baseValue))
            }

            rateLiveData.value = newCurrencyRates
        }
    }
}


