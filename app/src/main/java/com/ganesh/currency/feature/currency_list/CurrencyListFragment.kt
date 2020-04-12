package com.ganesh.currency.feature.currency_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ganesh.currency.R
import com.ganesh.currency.databinding.CurrencyListFragmentLayoutBinding
import com.ganesh.currency.feature.currency_list.adapter.CurrencyAdapter
import com.ganesh.currency.feature.currency_list.adapter.OnRateInteraction
import com.ganesh.currency.utill.Internet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyListFragment : Fragment(), OnRateInteraction {

    private lateinit var binding: CurrencyListFragmentLayoutBinding

    private val currencyViewModel: CurrencyViewModel by viewModel()

    private var canReferesh = true
    private val interval = 1000L
    private var adapter: CurrencyAdapter? = null

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
        adapter = CurrencyAdapter()
        binding.adapter = adapter
        adapter?.setListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObserver()
    }

    override fun onRateChanged(currencyName: String, value: Float, position: Int) {
        activity?.let {
            if (!Internet.isAvailable(it)) MaterialAlertDialogBuilder(activity).setMessage(Internet.errorMessage).show()
            currencyViewModel.setBase(currencyName, value)
        }
    }

    override fun onValueChanged(value: Float) {
        currencyViewModel.setNewBaseValue(value)
    }

    override fun scrollToTop() {
        binding.rcr.scrollToPosition(0)
    }

    private fun setupObserver() {
        currencyViewModel.errMessage.observe(viewLifecycleOwner, Observer {
            binding.errorMessage = it
            binding.errorTxtVisiableStatus = true
            binding.rcrVisibleStatus = false
        })

        currencyViewModel.rate.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.Main) {
                binding.adapter?.apply {
                    binding.rcrVisibleStatus = true
                    addItems(it)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            GlobalScope.launch {
                refresh()
            }
        }
    }

    private suspend fun refresh() = withContext(Dispatchers.Main) {
        while (canReferesh) {
            delay(interval)
            currencyViewModel.ratesFromRepo()
        }
    }
}
