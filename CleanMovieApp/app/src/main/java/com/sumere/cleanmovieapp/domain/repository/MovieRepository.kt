package com.sumere.cleanmovieapp.domain.repository

import com.sumere.cleanmovieapp.data.remote.dto.MovieDetailDto
import com.sumere.cleanmovieapp.data.remote.dto.MoviesDto

interface MovieRepository {
    suspend fun getMovies(search: String): MoviesDto
    suspend fun getMovieDetail(imdbId: String): MovieDetailDto
}