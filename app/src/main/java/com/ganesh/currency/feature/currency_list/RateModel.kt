package com.ganesh.currency.feature.currency_list

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
data class RateModel(
    val currency: String,
    val rate: Float,
    var value: Float,
    var countryName: String
)

data class RateRespose(val baseCurrency: String, val rates: Map<String, Float>?)
