package com.ganesh.currency.feature.currency_list.adapter

import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.currency.databinding.RateAdapterLayoutBinding
import com.ganesh.currency.feature.currency_list.RateModel

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyViewHolder(val binding: RateAdapterLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(_rateModel: RateModel) {
        "%.2f".format(_rateModel.value)
        binding.rateModel = _rateModel
    }

    fun bindTo(position: Int, valueWatcher: TextWatcher) {

        binding.edtxtRate.isEnabled = position == 0

        binding.edtxtRate.removeTextChangedListener(valueWatcher)

        // set on text changed listener
        if (position == 0)
            binding.edtxtRate.addTextChangedListener(valueWatcher)
    }
}
