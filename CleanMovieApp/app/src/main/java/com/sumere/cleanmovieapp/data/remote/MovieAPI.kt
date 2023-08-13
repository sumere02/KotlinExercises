package com.sumere.cleanmovieapp.data.remote

import com.sumere.cleanmovieapp.Util.Constants.API_KEY
import com.sumere.cleanmovieapp.data.remote.dto.MovieDetailDto
import com.sumere.cleanmovieapp.data.remote.dto.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString: String,
        @Query("apikey") apiKey: String = API_KEY
    ): MoviesDto

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = API_KEY
    ): MovieDetailDto
}