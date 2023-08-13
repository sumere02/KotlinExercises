package com.sumere.cleanmovieapp.domain.use_case.get_movies

import com.sumere.cleanmovieapp.Util.Resource
import com.sumere.cleanmovieapp.data.remote.dto.toMovieList
import com.sumere.cleanmovieapp.domain.model.Movie
import com.sumere.cleanmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    fun executeGetMovies(search: String): Flow<Resource<List<Movie>>> = flow{
        try {
            emit(Resource.Loading())
            val movieList = repository.getMovies(search)
            if(movieList.Response.equals("True")){
                emit(Resource.Success(movieList.toMovieList()))
            } else{
              emit(Resource.Error("Movie Not Found"))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Error Occurred When Getting Data"))
        }
    }

}