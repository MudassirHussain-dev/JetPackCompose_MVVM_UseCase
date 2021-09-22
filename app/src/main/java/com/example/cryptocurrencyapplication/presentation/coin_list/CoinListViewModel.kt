package com.example.cryptocurrencyapplication.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapplication.common.Resource
import com.example.cryptocurrencyapplication.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel
@Inject
constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CoinsListState())
    val state: State<CoinsListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = CoinsListState(coins = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        CoinsListState(error = it.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = CoinsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}