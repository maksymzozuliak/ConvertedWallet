package com.example.convertedwallet.model.internet

import com.example.convertedwallet.model.ApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getRates(
        @Query("symbols") symbols: String = "EUR%2CUAH%2CUSD",
        @Query("base") base: String,
        @Header("apikey") apiKey: String = ApiKey
    ) : Response<CurrencyResponse>

}