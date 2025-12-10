package com.tony.pelimart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tony.pelimart.data.repository.MovieRepository
import com.tony.pelimart.domain.model.Movie
import com.tony.pelimart.domain.model.Provider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProvidersViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ProvidersUiState>(ProvidersUiState.Loading)
    val uiState: StateFlow<ProvidersUiState> = _uiState.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private var currentPage = 1
    private var selectedProvider: Provider? = null

    init {
        fetchProviders()
    }

    fun fetchProviders() {
        viewModelScope.launch {
            movieRepository.getWatchProviders()
                .onStart { _uiState.value = ProvidersUiState.Loading }
                .catch { e -> _uiState.value = ProvidersUiState.Error(e.message ?: "Sin conexión, esta vista requiere internet") }
                .collect { providers ->
                    _uiState.value = ProvidersUiState.ProvidersList(providers)
                }
        }
    }

    fun onProviderSelected(provider: Provider) {
        selectedProvider = provider
        currentPage = 1
        _movies.value = emptyList()
        fetchMoviesByProvider()
    }

    fun fetchMoviesByProvider() {
        val providerId = selectedProvider?.id ?: return

        viewModelScope.launch {
            movieRepository.getMoviesByProvider(providerId, currentPage)
                .onStart { 
                    if (currentPage == 1) _uiState.value = ProvidersUiState.Loading
                    else _uiState.value = ProvidersUiState.LoadingMore
                }
                .catch { e -> _uiState.value = ProvidersUiState.Error(e.message ?: "An unknown error occurred") }
                .collect { newMovies ->
                    _movies.value += newMovies
                    _uiState.value = ProvidersUiState.MoviesList(_movies.value)
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

sealed class ProvidersUiState {
    object Loading : ProvidersUiState()
    data class ProvidersList(val providers: List<Provider>) : ProvidersUiState()
    data class MoviesList(val movies: List<Movie>) : ProvidersUiState()
    data class Error(val message: String) : ProvidersUiState()
    object LoadingMore : ProvidersUiState()
}