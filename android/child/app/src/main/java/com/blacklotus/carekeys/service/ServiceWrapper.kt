package com.blacklotus.carekeys.service

import android.util.Log
import com.blacklotus.carekeys.model.RequestTexts
import com.blacklotus.carekeys.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceWrapper {
        fun texts(requestTexts: RequestTexts, callback: Callback<Void>) {
            RetrofitInstance
                .getService()
                .texts(requestTexts)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
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

                    override fun onFailure(call: Call<Void>, t: Throwable) {
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