package com.ganesh.currency.feature.currency_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.currency.R
import com.ganesh.currency.databinding.RateAdapterLayoutBinding
import com.ganesh.currency.feature.currency_list.RateModel

/**
 * Created by ganeshkumarraja on 4/9/20.
 */
class CurrencyAdapter : RecyclerView.Adapter<CurrencyViewHolder>() {
    private var items: List<RateModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<RateAdapterLayoutBinding>(
            inflater,
            R.layout.rate_adapter_layout,
            parent,
            false
        )

        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = items.get(position)
        holder.bindItem(item)
    }

    fun addItems(_items: List<RateModel>) {
        items = _items
        notifyDataSetChanged()
    }
}