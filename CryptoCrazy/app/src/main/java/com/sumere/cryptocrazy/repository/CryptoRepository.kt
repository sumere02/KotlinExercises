package com.sumere.cryptocrazy.repository

import com.sumere.cryptocrazy.model.Crypto
import com.sumere.cryptocrazy.model.CryptoItem
import com.sumere.cryptocrazy.service.CryptoAPI
import com.sumere.cryptocrazy.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(private val api: CryptoAPI){
    suspend fun getCryptoList(): Resource<List<Crypto>> {
        val response = try {
            api.getCryptoList()
        } catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(): Resource<List<CryptoItem>>{
        val response = try{
            api.getCrypto()
        } catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}