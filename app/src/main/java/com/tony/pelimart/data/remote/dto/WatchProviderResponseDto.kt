package com.tony.pelimart.data.remote.dto

data class WatchProviderResponseDto(
    val id: Int,
    val results: Map<String, WatchProviderCountryDto>
)