package com.sumere.kmmmovieapp.di

import com.sumere.kmmmovieapp.data.remote.MovieService
import com.sumere.kmmmovieapp.data.remote.RemoteDataSource
import com.sumere.kmmmovieapp.data.repository.MovieRepositoryImpl
import com.sumere.kmmmovieapp.domain.repository.MovieRepository
import com.sumere.kmmmovieapp.domain.usecase.GetMovieUseCase
import com.sumere.kmmmovieapp.domain.usecase.GetMoviesUseCase
import com.sumere.kmmmovieapp.util.provideDispatcher
import org.koin.dsl.module

private val dataModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { MovieService() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    factory { GetMoviesUseCase() }
    factory { GetMovieUseCase() }
}

private val sharedModules = listOf(domainModule, dataModule, utilityModule)

fun getSharedModules() = sharedModules












