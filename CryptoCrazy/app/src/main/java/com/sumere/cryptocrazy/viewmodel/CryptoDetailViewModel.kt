package com.sumere.cryptocrazy.viewmodel

import androidx.lifecycle.ViewModel
import com.sumere.cryptocrazy.model.Crypto
import com.sumere.cryptocrazy.model.CryptoItem
import com.sumere.cryptocrazy.repository.CryptoRepository
import com.sumere.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(private val repository: CryptoRepository): ViewModel() {
    suspend fun getCrypto(): Resource<List<CryptoItem>>{
        return repository.getCrypto()
    }
}