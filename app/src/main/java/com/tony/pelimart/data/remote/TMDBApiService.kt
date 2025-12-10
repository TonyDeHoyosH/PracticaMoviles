package com.tony.pelimart.data.remote

import com.tony.pelimart.BuildConfig
import com.tony.pelimart.data.remote.dto.MovieDto
import com.tony.pelimart.data.remote.dto.MovieListDto
import com.tony.pelimart.data.remote.dto.ProviderListDto
import com.tony.pelimart.data.remote.dto.WatchProviderResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.TMDB_API_KEY
    }

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): MovieListDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieListDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDto

    @GET("watch/providers/movie")
    suspend fun getWatchProviders(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("watch_region") region: String = "MX"
    ): ProviderListDto

    @GET("discover/movie")
    suspend fun getMoviesByProvider(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("with_watch_providers") providerId: String,
        @Query("watch_region") region: String = "MX",
        @Query("page") page: Int
    ): MovieListDto

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): WatchProviderResponseDto
}