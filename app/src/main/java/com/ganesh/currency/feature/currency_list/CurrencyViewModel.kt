package com.ganesh.currency.feature.currency_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganesh.currency.data.Repository
import com.ganesh.currency.di.Country
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyViewModel(
    private val repository: Repository<RateRespose>,
    private val country: Country
) :
    ViewModel() {

    private var rateLiveData: MutableLiveData<List<RateModel>> = MutableLiveData()
    val rate: LiveData<List<RateModel>> = rateLiveData

    private var errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val errMessage: LiveData<String> = errorMessageLiveData

    // Default values
    private var baseCurrency: String = "EUR"
    private var baseValue: Float = 100F

    private val shynRateUpdate: Any = Object()

    fun setBase(_currency: String, _value: Float) {
        baseCurrency = _currency
        baseValue = _value

        ratesFromRepo()
    }

    fun setNewBaseValue(value: Float) {
        // Ignore so it doesn't enter an infinite loop
        if (baseValue.equals(value))
            return

        synchronized(shynRateUpdate) {
            baseValue = value

            val newCurrencyRates: MutableList<RateModel> = mutableListOf()

            rateLiveData.value?.forEach {
                newCurrencyRates.add(
                    RateModel(
                        it.currency,
                        it.rate,
                        it.rate * baseValue,
                        it.countryName
                    )
                )
            }

            rateLiveData.value = newCurrencyRates
        }
    }

    fun ratesFromRepo() {
        viewModelScope.launch {
            repository.doRequest(baseCurrency, {
                updateLatestRates(it)
            }, {
                if (rateLiveData.value.isNullOrEmpty()) errorMessageLiveData.value = it.message
            }
            )
        }

    }

    private fun updateLatestRates(latestRates: RateRespose) {

        val newCurrencyRates: MutableList<RateModel> = mutableListOf()

        synchronized(shynRateUpdate) {

            newCurrencyRates.add(
                RateModel(
                    latestRates.baseCurrency,
                    1.0F,
                    baseValue,
                    country.currencyMap[baseCurrency] ?: ""
                )
            )

            latestRates.rates?.forEach { (currency, rate) ->
                newCurrencyRates.add(
                    RateModel(
                        currency,
                        rate,
                        rate * baseValue,
                        country.currencyMap[currency] ?: ""
                    )
                )
            }

            rateLiveData.value = newCurrencyRates
        }
    }
}
