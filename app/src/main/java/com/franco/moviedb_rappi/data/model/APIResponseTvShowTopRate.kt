package com.franco.moviedb_rappi.data.model

data class APIResponseTvShowTopRate(
        val page: Int,
        val results: List<TvShowTopRate>,
        val total_pages: Int,
        val total_results: Int
)
