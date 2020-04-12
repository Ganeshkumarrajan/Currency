package com.ganesh.currency.data

import com.ganesh.currency.feature.currency_list.RateRespose
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ganeshkumarraja on 4/9/20.
 */
class RepositoryImpl(
    private val service: Service,
    private val scheduler: Scheduler,
    private val androidScheduler: Scheduler
) : Repository<RateRespose> {
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun doRequest(
        base: String,
        success: (RateRespose) -> Unit,
        eror: (Throwable) -> Unit
    ) {
        disposable.add(
            service.getRates(base)
                .subscribeOn(scheduler)
                .observeOn(androidScheduler)
                .subscribe(success, eror)
        )
    }

    override fun deAlloc() {
        disposable.clear()
    }
}
