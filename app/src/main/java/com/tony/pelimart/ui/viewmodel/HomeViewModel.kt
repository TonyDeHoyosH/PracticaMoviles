package com.tony.pelimart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tony.pelimart.domain.model.Movie
import com.tony.pelimart.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private var currentPage = 1

    init {
        fetchTrendingMovies()
    }

    fun fetchTrendingMovies() {
        viewModelScope.launch {
            movieRepository.getTrendingMovies(currentPage)
                .onStart { 
                    if (currentPage == 1) _uiState.value = HomeUiState.Loading
                    else _uiState.value = HomeUiState.LoadingMore 
                }
                .catch { e -> _uiState.value = HomeUiState.Error(e.message ?: "An unknown error occurred") }
                .collect { newMovies ->
                    _movies.value += newMovies
                    _uiState.value = HomeUiState.Success(_movies.value)
                    currentPage++
                }
        }
    }
    
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                movieRepository.removeMovieFromFavorites(movie.id)
            } else {
                movieRepository.addMovieToFavorites(movie)
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val movies: List<Movie>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    object LoadingMore : HomeUiState()
}