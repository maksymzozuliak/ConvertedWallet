package com.example.convertedwallet.model.repository

import com.example.convertedwallet.model.internet.*
import java.text.DateFormatSymbols
import javax.inject.Inject

class InternetRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : InternetRepository {

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base = base)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }
}