package com.example.cryptocurrencyapplication.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencyapplication.common.Constants
import com.example.cryptocurrencyapplication.common.Resource
import com.example.cryptocurrencyapplication.domain.use_case.get_coin.GetCoinUseCase
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CoinDetailViewModel
@Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            getCoinDetail(it)
        }

    }

    private fun getCoinDetail(coinId: String) {
        getCoinUseCase(coinId).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = it.data)
                }
                is Resource.Error -> {
                    _state.value =
                        CoinDetailState(error = it.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }
            }
        }
    }

}