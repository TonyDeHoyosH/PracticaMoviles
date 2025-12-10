package com.tony.pelimart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tony.pelimart.domain.model.Movie
import com.tony.pelimart.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        movieRepository.getFavoriteMovies()
            .onEach { movies ->
                if (movies.isEmpty()) {
                    _uiState.value = FavoritesUiState.Empty
                } else {
                    _uiState.value = FavoritesUiState.Success(movies)
                }
            }
            .catch { e -> _uiState.value = FavoritesUiState.Error(e.message ?: "An unknown error occurred") }
            .launchIn(viewModelScope)
    }
    
    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavorites(movie.id)
        }
    }
}

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()
    data class Success(val movies: List<Movie>) : FavoritesUiState()
    object Empty : FavoritesUiState()
    data class Error(val message: String) : FavoritesUiState()
}