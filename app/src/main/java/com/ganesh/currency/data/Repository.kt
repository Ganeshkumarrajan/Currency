package com.ganesh.currency.data


/**
 * Created by ganeshkumarraja on 4/9/20.
 */
interface  Repository<R> {
     fun doRequest(
        base:String,
        success: (R) -> Unit,
        eror: (Throwable) -> Unit
    )

     fun deAlloc()
}