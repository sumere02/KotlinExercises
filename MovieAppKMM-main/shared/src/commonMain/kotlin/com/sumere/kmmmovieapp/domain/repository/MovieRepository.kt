package com.sumere.kmmmovieapp.domain.repository

import com.sumere.kmmmovieapp.domain.model.Movie

internal interface MovieRepository {
    suspend fun getMovies(page: Int): List<Movie>

    suspend fun getMovie(movieId: Int): Movie
}