package com.example.cryptocurrencyapplication.domain.use_case.get_coin

import com.example.cryptocurrencyapplication.common.Resource
import com.example.cryptocurrencyapplication.data.remote.dto.toCoinDetail
import com.example.cryptocurrencyapplication.domain.model.CoinDetail
import com.example.cryptocurrencyapplication.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(id: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(id).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occured")
            )
        } catch (e: IOException) {
            emit(Resource.Error<CoinDetail>("Couldn't reach server. Check your internet connection."))
        }

    }

}