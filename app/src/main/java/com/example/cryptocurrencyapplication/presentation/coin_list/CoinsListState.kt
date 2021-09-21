package com.example.cryptocurrencyapplication.presentation.coin_list

import com.example.cryptocurrencyapplication.domain.model.Coin

data class CoinsListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
