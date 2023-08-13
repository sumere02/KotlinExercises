package com.sumere.cleanmovieapp.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumere.cleanmovieapp.Util.Resource
import com.sumere.cleanmovieapp.domain.use_case.get_movies.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
): ViewModel() {
    private val _state = mutableStateOf<MoviesState>(MoviesState())
    val state: State<MoviesState> = _state
    private var job: Job? = null

    private fun getMovies(search: String){
        job?.cancel()
        job = getMovieUseCase.executeGetMovies(search).onEach {
            when(it){
                is Resource.Success -> {
                    _state.value = MoviesState(movies = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = MoviesState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MoviesState(error = it.message ?: "Error")
                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MoviesEvent){
        when(event){
            is MoviesEvent.Search -> {
                getMovies(event.searchString)
            }
        }
    }

    init {
        getMovies(_state.value.search)
    }
}