package com.example.convertedwallet.model.repository

import com.example.convertedwallet.model.internet.CurrencyResponse
import com.example.convertedwallet.model.internet.Resource

interface InternetRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>

}