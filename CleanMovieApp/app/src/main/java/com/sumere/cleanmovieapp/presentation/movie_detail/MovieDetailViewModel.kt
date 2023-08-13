package com.sumere.cleanmovieapp.presentation.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumere.cleanmovieapp.Util.Resource
import com.sumere.cleanmovieapp.domain.use_case.get_movie_detail.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.sumere.cleanmovieapp.Util.Constants.IMDB_ID

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val stateHandle: SavedStateHandle
): ViewModel() {
    private val _state = mutableStateOf<MovieDetailState>(MovieDetailState())
    val state: State<MovieDetailState> = _state

    private fun getMovieDetail(imdbId: String){
        getMovieDetailsUseCase.executeGetMovieDetails(imdbId).onEach {
            when(it){
                is Resource.Success -> {
                    _state.value = MovieDetailState(movie = it.data)
                }
                is Resource.Loading -> {
                    _state.value = MovieDetailState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MovieDetailState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }
    init {
        stateHandle.get<String>(IMDB_ID)?.let {
            getMovieDetail(it)
        }
    }

}