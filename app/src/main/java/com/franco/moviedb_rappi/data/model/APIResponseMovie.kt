package com.franco.moviedb_rappi.data.model

data class APIResponseMovie(
    val page: Int,
    var results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)