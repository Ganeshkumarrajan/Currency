package com.ganesh.currency.feature.currency_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ganesh.currency.R
import com.ganesh.currency.databinding.CurrencyListFragmentLayoutBinding
import com.ganesh.currency.feature.currency_list.adapter.CurrencyAdapter
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyListFragment : Fragment() {

    private lateinit var binding: CurrencyListFragmentLayoutBinding

    private val currencyViewModel: CurrencyViewModel by viewModel()

    private var canReferesh = true
    private val interval = 1000L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.currency_list_fragment_layout,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = CurrencyAdapter()
        setupObserver()
    }

    fun setupObserver() {
        currencyViewModel.errMessage.observe(viewLifecycleOwner, Observer {
            activity?.let { activity ->
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })

        currencyViewModel.rate.observe(viewLifecycleOwner, Observer {
            binding.adapter?.apply {
                addItems(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            referesh()
        }

    }

    suspend fun referesh() = withContext(Dispatchers.Main)
    {
        while (canReferesh) {
            delay(interval)
            currencyViewModel.ratesFromRepo()
        }
    }
}



