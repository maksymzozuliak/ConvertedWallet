package com.example.convertedwallet.ui.MainScreen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertedwallet.model.Money
import com.example.convertedwallet.model.internet.Resource
import com.example.convertedwallet.model.languageSpKey
import com.example.convertedwallet.model.repository.InternetRepository
import com.example.convertedwallet.model.updateSingleRate
import com.example.convertedwallet.model.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val internetRepository: InternetRepository,
    private val useCases: UseCases,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    init {
        getMoney()
    }

    private var getMoneyJob: Job? = null

    private val _moneyList = mutableStateOf(listOf<Money>())
    val moneyList: State<List<Money>> = _moneyList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentCurrency = mutableStateOf(BaseCurrency.UAH)
    val currentCurrency: State<BaseCurrency> = _currentCurrency

//    private val _currentBase = mutableStateOf(
//        sharedPreferences.getString(languageSpKey,"UAH")?: "UAH"
//    )
//    val currentBase: State<String> = _currentBase

    sealed class MoneyEvent {

        class UpdateRates(val base : String): MoneyEvent()
        class SaveMoney(val money: Money): MoneyEvent()
        object AddMoney : MoneyEvent()
        class DeleteMoney(val money: Money): MoneyEvent()
        class ChangeCurrency(val currency: BaseCurrency): MoneyEvent()

    }

    fun onEvent(event: MoneyEvent) {
        when(event) {
            is MoneyEvent.UpdateRates ->{
                viewModelScope.launch {
                    sharedPreferences.edit()
                        .putString(languageSpKey,event.base)
                        .apply()
                    updateRates()
                }
            }
            is MoneyEvent.SaveMoney ->{
                viewModelScope.launch {
                    useCases.addMoney(event.money)
                    updateRates()
                }
            }
            is MoneyEvent.DeleteMoney ->{
                viewModelScope.launch {
                    useCases.deleteMoney(event.money)
                }
            }
            is MoneyEvent.ChangeCurrency ->{
                _currentCurrency.value = event.currency
            }
            is MoneyEvent.AddMoney -> {
                val newList = _moneyList.value.toMutableList()
                newList.add(Money(
                    currency = "Cur",
                    inCurrency = 0.0,
                    rateToUAH = 0.0,
                    rateToEUR = 0.0,
                    rateToUSD = 0.0

                )
                )
            }
        }
    }

    private fun updateRates() {

        _isLoading.value = true

        viewModelScope.launch {

            val responseUAH = internetRepository.getRates(BaseCurrency.UAH.string)
            val responseUSD = internetRepository.getRates(BaseCurrency.USD.string)
            val responseEUR = internetRepository.getRates(BaseCurrency.EUR.string)

            if (responseUAH is Resource.Success &&
                    responseEUR is Resource.Success &&
                    responseUSD is Resource.Success
            ) {

                _moneyList.value.forEach { money ->
                    useCases.addMoney(money.copy(
                        rateToUAH = money.updateSingleRate(responseUAH.data!!.rates),
                        rateToUSD = money.updateSingleRate(responseUSD.data!!.rates),
                        rateToEUR = money.updateSingleRate(responseEUR.data!!.rates)
                    ))
                }
                _isLoading.value = false
            } else {
                _isLoading.value = false
            }
        }
    }

    private fun getMoney() {
        getMoneyJob?.cancel()
        getMoneyJob = useCases.getMoneyList()
            .onEach { money ->
                _moneyList.value = money
            }
            .launchIn(viewModelScope)
    }

    enum class BaseCurrency(val string: String) {
        UAH("UAH"),USD("USD"),EUR("EUR")
    }
}