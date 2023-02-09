package com.example.convertedwallet.model.repository

import com.example.convertedwallet.model.Money
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {

    fun getMoneyList(): Flow<List<Money>>

    suspend fun getMoneyById(id: Int): Money?

    suspend fun insertMoney(money: Money)

    suspend fun deleteMoney(money: Money)
}