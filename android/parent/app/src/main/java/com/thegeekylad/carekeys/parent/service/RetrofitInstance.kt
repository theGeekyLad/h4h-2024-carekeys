package com.thegeekylad.carekeys.parent.service

import com.thegeekylad.carekeys.parent.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitInstance {
    companion object {
        private var retrofit: Retrofit? = null
        private var service: Service? = null

        fun getService(): Service {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://${Constants.HOST}:${Constants.PORT}/")
                    .build()

                service = retrofit!!.create(Service::class.java)
            }

            return service!!
        }
    }
}