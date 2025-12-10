package com.tony.pelimart.data.remote.dto

data class WatchProviderCountryDto(
    val link: String,
    val flatrate: List<ProviderDto>?,
    val rent: List<ProviderDto>?,
    val buy: List<ProviderDto>?
)