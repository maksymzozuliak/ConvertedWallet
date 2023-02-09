package com.example.convertedwallet.model.use_cases

import com.example.convertedwallet.model.Money
import com.example.convertedwallet.model.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow

data class UseCases (
    val addMoney: AddMoney,
    val deleteMoney: DeleteMoney,
    val getMoney: GetMoney,
    val getMoneyList: GetMoneyList
)

class AddMoney(
    private val repository: MoneyRepository
) {

    suspend operator fun invoke(money: Money) {

        repository.insertMoney(money)

    }
}

class DeleteMoney(
    private val repository: MoneyRepository
) {

    suspend operator fun invoke(money: Money) {

        repository.deleteMoney(money)

    }
}

class GetMoney(
    private val repository: MoneyRepository
) {

    suspend operator fun invoke(id: Int) : Money? {

        return repository.getMoneyById(id)

    }
}

class GetMoneyList(
    private val repository: MoneyRepository
) {

    operator fun invoke() : Flow<List<Money>> {

        return repository.getMoneyList()

    }
}