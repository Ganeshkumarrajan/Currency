package com.ganesh.currency.data

import com.ganesh.currency.feature.currency_list.RateRespose
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ganeshkumarraja on 4/1/20.
 */
interface Service {
    @GET("latest")
    fun getRates(@Query("base") baseStr: String): Single<RateRespose>
}
