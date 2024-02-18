package com.thegeekylad.carekeys.parent.service

import android.util.Log
import com.thegeekylad.carekeys.parent.model.ResponseBarGraph
import com.thegeekylad.carekeys.parent.util.Constants.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceWrapper {

        fun barGraph(callback: Callback<ResponseBarGraph>) {
            RetrofitInstance
                .getService()
                .barGraph()
                .enqueue(object : Callback<ResponseBarGraph> {
                    override fun onResponse(
                        call: Call<ResponseBarGraph>,
                        response: Response<ResponseBarGraph>
                    ) {
                        Log.d(
                            TAG,
                            "${javaClass.enclosingMethod?.name} success."
                        )
                        if (!response.isSuccessful)
                            Log.d(
                                TAG,
                                "${javaClass.enclosingMethod?.name} failed! " +
                                        "Code: ${response.code()}"
                            )
                        callback.onResponse(call, response)
                    }

                    override fun onFailure(call: Call<ResponseBarGraph>, t: Throwable) {
                        Log.d(
                            TAG,
                            "${javaClass.enclosingMethod?.name} failed."
                        )
                        t.printStackTrace()
                        callback.onFailure(call, t)
                    }
                })
        }
}