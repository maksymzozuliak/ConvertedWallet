package com.example.convertedwallet.model.repository

import com.example.convertedwallet.model.Money
import com.example.convertedwallet.model.database.MoneyDao
import kotlinx.coroutines.flow.Flow

class MoneyRepositoryImpl(
    private val dao: MoneyDao
): MoneyRepository {

    override fun getMoneyList(): Flow<List<Money>> {
        return dao.getMoneyList()
    }

    override suspend fun getMoneyById(id: Int): Money? {
        return dao.getMoneyById(id)
    }

    override suspend fun insertMoney(money: Money) {
        return dao.insertMoney(money)
    }

    override suspend fun deleteMoney(money: Money) {
        return dao.deleteMoney(money)
    }

}