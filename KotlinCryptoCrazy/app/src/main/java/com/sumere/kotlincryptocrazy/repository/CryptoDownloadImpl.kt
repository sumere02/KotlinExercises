package com.sumere.kotlincryptocrazy.repository

import com.sumere.kotlincryptocrazy.model.CryptoModel
import com.sumere.kotlincryptocrazy.service.CryptoAPI
import com.sumere.kotlincryptocrazy.util.Resource

class CryptoDownloadImpl(private val api: CryptoAPI): CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
        return try {
            val response = api.getData()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else{
                Resource.error("Error",null)
            }
        } catch (e: Exception){
            Resource.error("No Data!",null)
        }
    }
}