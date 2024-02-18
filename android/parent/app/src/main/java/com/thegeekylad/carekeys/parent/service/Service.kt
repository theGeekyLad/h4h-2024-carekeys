package com.thegeekylad.carekeys.parent.service

import com.thegeekylad.carekeys.parent.model.ResponseBarGraph
import retrofit2.Call
import retrofit2.http.POST

interface Service {
    @POST("barGraph")
    fun barGraph(): Call<ResponseBarGraph>
}