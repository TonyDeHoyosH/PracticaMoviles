package com.tony.pelimart.data

import com.tony.pelimart.data.local.FavoriteMovieEntity
import com.tony.pelimart.data.remote.dto.MovieDto
import com.tony.pelimart.data.remote.dto.ProviderDto
import com.tony.pelimart.domain.model.Movie
import com.tony.pelimart.domain.model.Provider

fun MovieDto.toMovie(isFavorite: Boolean = false): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = "https://image.tmdb.org/t/p/w500${poster_path}",
        rating = vote_average,
        isFavorite = isFavorite
    )
}

fun Movie.toFavoriteMovieEntity(genres: String = "", providers: String = ""): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        rating = rating,
        genres = genres, // Fetched from movie details endpoint
        providers = providers // Fetched from movie providers endpoint
    )
}

fun FavoriteMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        rating = rating,
        isFavorite = true
    )
}

fun ProviderDto.toProvider(): Provider {
    return Provider(
        id = provider_id,
        name = provider_name,
        logoUrl = "https://image.tmdb.org/t/p/w500${logo_path}"
    )
}
