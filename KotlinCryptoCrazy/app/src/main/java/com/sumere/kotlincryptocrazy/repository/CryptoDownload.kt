package com.sumere.kotlincryptocrazy.repository

import com.sumere.kotlincryptocrazy.model.CryptoModel
import com.sumere.kotlincryptocrazy.util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos(): Resource<List<CryptoModel>>
}