package com.tony.pelimart.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tony.pelimart.data.local.MovieDatabase
import com.tony.pelimart.data.remote.TMDBApiService
import com.tony.pelimart.data.repository.MovieRepository
import com.tony.pelimart.ui.viewmodel.FavoritesViewModel
import com.tony.pelimart.ui.viewmodel.HomeViewModel
import com.tony.pelimart.ui.viewmodel.ProvidersViewModel
import com.tony.pelimart.ui.viewmodel.SearchViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    private fun provideMovieRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getDatabase(context)
        val apiService = Retrofit.Builder()
            .baseUrl(TMDBApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApiService::class.java)
        return MovieRepository(apiService, database.movieDao())
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                        HomeViewModel(provideMovieRepository(context)) as T
                    }
                    modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                        SearchViewModel(provideMovieRepository(context)) as T
                    }
                     modelClass.isAssignableFrom(ProvidersViewModel::class.java) -> {
                        ProvidersViewModel(provideMovieRepository(context)) as T
                    }
                    modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                        FavoritesViewModel(provideMovieRepository(context)) as T
                    }
                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }
    }
}
