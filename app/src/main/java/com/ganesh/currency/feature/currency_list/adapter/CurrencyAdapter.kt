package com.ganesh.currency.feature.currency_list.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.currency.R
import com.ganesh.currency.databinding.RateAdapterLayoutBinding
import com.ganesh.currency.feature.currency_list.RateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by ganeshkumarraja on 4/9/20.
 */
class CurrencyAdapter : RecyclerView.Adapter<CurrencyViewHolder>() {
    private var items: List<RateModel>
    lateinit var callback: OnRateInteraction
    private val valueWatcher: TextWatcher

    init {
        items = listOf()

        /** We set out TextWatcher here so it can be reused
         *  This will pick up the value being entered in the root rate only
         */
        this.valueWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(newValue: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(newValue: Editable?) {

                val strValue: String = newValue.toString().trim()
                var value: Float

                value = try {
                    strValue.toFloat()
                } catch (e: Exception) {
                    0F
                }

                items[0].value = value
                callback.onValueChanged(value)
            }
        }
    }

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
        val item = items[position]

        holder.binding.parentLayout.setOnClickListener {
            val pos: Int = holder.adapterPosition

            if (pos != RecyclerView.NO_POSITION && pos > 0) {
                callback.onRateChanged(items[pos].currency, items[pos].value, pos)
            }
        }

        holder.bindTo(position, valueWatcher)
        holder.bindItem(item)
    }


    fun addItems(_items: List<RateModel>) {
        //update UI on main thread
        GlobalScope.launch(Dispatchers.Main) {
            var newRootRate = false

            if (items.isNotEmpty())
                newRootRate = (items[0].currency != _items[0].currency)

            // finding difference to maximize UI performance
            val diff = CurrencyAdapterDiff(items, _items)
            items = _items
            val result = DiffUtil.calculateDiff(diff)
            result.dispatchUpdatesTo(this@CurrencyAdapter)

            // scrolling to top if current position is in some where
            if (newRootRate)
                callback.scrollToTop()
        }
    }

    fun setListener(callback: OnRateInteraction) {
        this.callback = callback
    }
}

interface OnRateInteraction {
    fun onRateChanged(currencyName: String, value: Float, position: Int)
    fun onValueChanged(value: Float)
    fun scrollToTop()
}
