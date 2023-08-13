package com.sumere.cryptocrazy.service

import com.sumere.cryptocrazy.model.Crypto
import com.sumere.cryptocrazy.model.CryptoItem
import com.sumere.cryptocrazy.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {

    @GET("cryptolist.json")
    suspend fun getCryptoList(): List<Crypto>

    @GET("crypto.json")
    suspend fun getCrypto(): List<CryptoItem>

    /*
    //API
    @GET("prices")
    suspend fun getCryptoList(
        @Query("key") key: String
    ): Unit

    @GET("currencies")
    suspend fun getCrypto(
        @Query("key") key: String,
        @Query("id") id: String,
        @Query("attributes") attributes: String

    ): Unit
    */
}
