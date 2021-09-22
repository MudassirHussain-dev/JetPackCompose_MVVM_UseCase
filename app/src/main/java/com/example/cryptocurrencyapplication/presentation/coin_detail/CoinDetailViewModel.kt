package com.example.cryptocurrencyapplication.presentation.coin_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencyapplication.common.Constants
import com.example.cryptocurrencyapplication.common.Resource
import com.example.cryptocurrencyapplication.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel
@Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            getCoinDetail(it)
            Log.d("coins", "start CoinListScreen: "+it)

        }

    }

    private fun getCoinDetail(coinId: String) {
        getCoinUseCase(coinId).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = it.data)
                    Log.d("coins", "data CoinListScreen: "+it.data)
                }
                is Resource.Error -> {
                    _state.value =
                        CoinDetailState(error = it.message ?: "An unexpected error occured")
                    Log.d("coins", "error CoinListScreen: "+it.message)
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)

                    Log.d("coins", "loadin start CoinListScreen: "+it)
                }
            }
        }
    }

}