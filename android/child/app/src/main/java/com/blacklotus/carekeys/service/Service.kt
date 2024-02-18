package com.blacklotus.carekeys.service

import com.blacklotus.carekeys.model.RequestTexts
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {
    @POST("text")
    fun texts(@Body requestTexts: RequestTexts): Call<Void>
}