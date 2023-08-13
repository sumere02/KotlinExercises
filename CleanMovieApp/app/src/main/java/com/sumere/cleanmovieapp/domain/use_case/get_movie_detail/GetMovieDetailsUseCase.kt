package com.sumere.cleanmovieapp.domain.use_case.get_movie_detail

import com.sumere.cleanmovieapp.Util.Resource
import com.sumere.cleanmovieapp.data.remote.dto.toMovieDetail
import com.sumere.cleanmovieapp.domain.model.MovieDetail
import com.sumere.cleanmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {
    fun executeGetMovieDetails(imdbId: String): Flow<Resource<MovieDetail>> = flow{
        try {
            emit(Resource.Loading())
            val movieList = repository.getMovieDetail(imdbId)
            emit(Resource.Success(movieList.toMovieDetail()))
        } catch (e: Exception){
            emit(Resource.Error(message = "Error Occurred When Getting Data"))
        }
    }
}