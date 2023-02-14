package com.example.convertedwallet.model.repository

import com.example.convertedwallet.model.internet.CurrencyApi
import com.example.convertedwallet.model.internet.CurrencyResponse
import com.example.convertedwallet.model.internet.Resource
import javax.inject.Inject

class InternetRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : InternetRepository {

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
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