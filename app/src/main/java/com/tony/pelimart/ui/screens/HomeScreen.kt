package com.tony.pelimart.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.tony.pelimart.ui.components.LoadingSpinner
import com.tony.pelimart.ui.components.MovieCard
import com.tony.pelimart.ui.viewmodel.HomeUiState
import com.tony.pelimart.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is HomeUiState.Loading -> LoadingSpinner()
        is HomeUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClick = { /* TODO: Show BottomSheet */ },
                        onFavoriteClick = { viewModel.toggleFavorite(it) }
                    )
                }
            }
        }
        is HomeUiState.Error -> Text(text = state.message)
        is HomeUiState.LoadingMore -> { /* TODO: Show small spinner at the bottom */ }
    }
}