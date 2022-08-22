package com.natiqhaciyef.retrofitcomposecryptocurrencyapp.service

import com.natiqhaciyef.retrofitcomposecryptocurrencyapp.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

//https://raw.githubusercontent.com/
// atilsamancioglu/K21-JSONDataSet/master/crypto.json


interface CryptoApi {
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Call<List<CryptoModel>>
}