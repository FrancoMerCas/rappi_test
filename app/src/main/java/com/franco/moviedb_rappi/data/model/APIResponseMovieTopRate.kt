package com.franco.moviedb_rappi.data.model


data class APIResponseMovieTopRate(
        val page: Int,
        var results: List<MovieTopRate>,
        val total_pages: Int,
        val total_results: Int
)