package com.sumere.cleanmovieapp.data.repository

import com.sumere.cleanmovieapp.data.remote.MovieAPI
import com.sumere.cleanmovieapp.data.remote.dto.MovieDetailDto
import com.sumere.cleanmovieapp.data.remote.dto.MoviesDto
import com.sumere.cleanmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieAPI): MovieRepository {
    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(search)
    }

    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId)
    }
}