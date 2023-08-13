package com.sumere.cryptocrazy.dependencyInjection

import com.sumere.cryptocrazy.repository.CryptoRepository
import com.sumere.cryptocrazy.service.CryptoAPI
import com.sumere.cryptocrazy.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(
        api: CryptoAPI
    ) = CryptoRepository(api)


    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }
}