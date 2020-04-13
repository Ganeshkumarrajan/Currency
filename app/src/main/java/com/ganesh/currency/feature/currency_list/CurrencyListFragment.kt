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
import kotlin.coroutines.CoroutineContext

/**
 * Created by ganeshkumarraja on 4/2/20.
 */
class CurrencyListFragment : Fragment(), OnRateInteraction, CoroutineScope {

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

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
        mJob = Job()
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

    override fun onDestroy() {
        mJob.cancel()
        super.onDestroy()
    }

    //callback method for currency adapter
    override fun onRateChanged(currencyName: String, value: Float, position: Int) {
        activity?.let {
            if (!Internet.isAvailable(it)) MaterialAlertDialogBuilder(activity).setMessage(Internet.errorMessage).show()
            currencyViewModel.setBase(currencyName, value)
        }
    }

    //callback method for currency adapter
    override fun onValueChanged(value: Float) {
        currencyViewModel.setNewBaseValue(value)
    }

    //callback method for currency adapter
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
        launch {
            refresh()
        }
    }

    private suspend fun refresh() {
        while (canReferesh) {
            delay(interval)
            currencyViewModel.ratesFromRepo()
        }
    }


}
