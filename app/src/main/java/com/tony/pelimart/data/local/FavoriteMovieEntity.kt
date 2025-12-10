package com.tony.pelimart.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val rating: Double,
    val genres: String, // Storing as a comma-separated string
    val providers: String // Storing as a comma-separated string of provider names
)