package com.tony.pelimart.data.repository

import com.tony.pelimart.data.local.MovieDao
import com.tony.pelimart.data.remote.TMDBApiService
import com.tony.pelimart.data.toFavoriteMovieEntity
import com.tony.pelimart.data.toMovie
import com.tony.pelimart.data.toProvider
import com.tony.pelimart.domain.model.Movie
import com.tony.pelimart.domain.model.Provider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val apiService: TMDBApiService,
    private val movieDao: MovieDao
) {

    fun getTrendingMovies(page: Int): Flow<List<Movie>> = flow {
        val moviesFromApi = apiService.getTrendingMovies(page = page).results
        emit(moviesFromApi)
    }.combine(getFavoriteMovieIds()) { moviesDto, favoriteIds ->
        moviesDto.map { movieDto ->
            movieDto.toMovie(isFavorite = favoriteIds.contains(movieDto.id))
        }
    }

    fun searchMovies(query: String, page: Int): Flow<List<Movie>> = flow {
        val moviesFromApi = apiService.searchMovies(query = query, page = page).results
        emit(moviesFromApi)
    }.combine(getFavoriteMovieIds()) { moviesDto, favoriteIds ->
        moviesDto.map { movieDto ->
            movieDto.toMovie(isFavorite = favoriteIds.contains(movieDto.id))
        }
    }

    fun getWatchProviders(): Flow<List<Provider>> = flow {
        val providersFromApi = apiService.getWatchProviders().results
        emit(providersFromApi.map { it.toProvider() })
    }

    fun getMoviesByProvider(providerId: Int, page: Int): Flow<List<Movie>> = flow {
        val moviesFromApi = apiService.getMoviesByProvider(providerId = providerId.toString(), page = page).results
        emit(moviesFromApi)
    }.combine(getFavoriteMovieIds()) { moviesDto, favoriteIds ->
        moviesDto.map { movieDto ->
            movieDto.toMovie(isFavorite = favoriteIds.contains(movieDto.id))
        }
    }

    fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies().map { entities ->
            entities.map { it.toMovie() }
        }
    }

    fun getFavoriteMovieIds(): Flow<Set<Int>> {
        return movieDao.getFavoriteMovieIds().map { it.toSet() }
    }

    suspend fun addMovieToFavorites(movie: Movie) {
        movieDao.insertFavoriteMovie(movie.toFavoriteMovieEntity())
    }

    suspend fun removeMovieFromFavorites(movieId: Int) {
        movieDao.deleteFavoriteMovie(movieId)
    }
}