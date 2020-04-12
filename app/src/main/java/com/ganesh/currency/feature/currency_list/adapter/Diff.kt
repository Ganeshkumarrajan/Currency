package com.ganesh.currency.feature.currency_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ganesh.currency.feature.currency_list.RateModel

/**
 * Created by ganeshkumarraja on 4/10/20.
 */
class Diff(private val oldItems: List<RateModel>, private val newItems: List<RateModel>) :
    DiffUtil.Callback() {

    companion object {
        const val VALUE_CHG = "VALUE_CHG"
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].currency == newItems[newItemPosition].currency
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldRate = oldItems[oldItemPosition]
        val newRate = newItems[newItemPosition]

        val payloadSet = mutableSetOf<String>()

        if (oldRate.value != newRate.value)
            payloadSet.add(VALUE_CHG)

        return payloadSet
    }
}
