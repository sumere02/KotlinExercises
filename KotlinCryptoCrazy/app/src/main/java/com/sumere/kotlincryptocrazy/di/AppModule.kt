package com.sumere.kotlincryptocrazy.di

import com.sumere.kotlincryptocrazy.repository.CryptoDownload
import com.sumere.kotlincryptocrazy.repository.CryptoDownloadImpl
import com.sumere.kotlincryptocrazy.service.CryptoAPI
import com.sumere.kotlincryptocrazy.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{
    //Singleton Scope
    single {
        val BASE_URL = "https://raw.githubusercontent.com/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }

    single<CryptoDownload> {
        CryptoDownloadImpl(get())
    }

    viewModel {
        CryptoViewModel(get())
    }

    //Creates instance every time when injected
    factory {

    }
}