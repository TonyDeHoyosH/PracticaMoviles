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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private var currentPage = 1
    private var currentQuery = ""

    fun searchMovies(query: String) {
        if (query.isBlank()) return

        currentQuery = query
        currentPage = 1
        _movies.value = emptyList()

        viewModelScope.launch {
            movieRepository.searchMovies(currentQuery, currentPage)
                .onStart { _uiState.value = SearchUiState.Loading }
                .catch { e -> _uiState.value = SearchUiState.Error(e.message ?: "An unknown error occurred") }
                .collect { newMovies ->
                    if (newMovies.isEmpty()) {
                        _uiState.value = SearchUiState.Empty
                    } else {
                        _movies.value = newMovies
                        _uiState.value = SearchUiState.Success(_movies.value)
                        currentPage++
                    }
                }
        }
    }

    fun loadMoreResults() {
        viewModelScope.launch {
            movieRepository.searchMovies(currentQuery, currentPage)
                .onStart { _uiState.value = SearchUiState.LoadingMore }
                .catch { e -> _uiState.value = SearchUiState.Error(e.message ?: "An unknown error occurred") }
                .collect { newMovies ->
                    _movies.value += newMovies
                    _uiState.value = SearchUiState.Success(_movies.value)
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

sealed class SearchUiState {
    object Initial : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val movies: List<Movie>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    object Empty : SearchUiState()
    object LoadingMore : SearchUiState()
}