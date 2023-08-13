package com.sumere.cleanmovieapp.presentation.movies

import com.sumere.cleanmovieapp.domain.model.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val search: String = "batman"
)