package com.franco.moviedb_rappi.data.model

data class APIResponseTv(
        val page: Int,
        val results: List<TvShow>,
        val total_pages: Int,
        val total_results: Int
)