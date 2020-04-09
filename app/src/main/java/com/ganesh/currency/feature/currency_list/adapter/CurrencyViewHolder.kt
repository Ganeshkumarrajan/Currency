package com.ganesh.currency.feature.currency_list.adapter


import androidx.recyclerview.widget.RecyclerView
import com.ganesh.currency.databinding.RateAdapterLayoutBinding
import com.ganesh.currency.feature.currency_list.RateModel

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyViewHolder(val binding: RateAdapterLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(_rateModel: RateModel) {
        binding.rateModel = _rateModel
    }
}