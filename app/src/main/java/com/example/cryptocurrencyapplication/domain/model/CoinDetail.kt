package com.example.cryptocurrencyapplication.domain.model

import com.example.cryptocurrencyapplication.data.remote.dto.*

data class CoinDetail(
    val id: String,
    val description: String,
    val name: String,
    val is_active: Boolean,
    val rank: Int,
    val symbol: String,
    val tags: List<String>,
    val team: List<TeamMember>,
)
