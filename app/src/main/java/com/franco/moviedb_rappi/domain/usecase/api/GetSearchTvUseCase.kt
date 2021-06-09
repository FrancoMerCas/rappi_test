package com.franco.moviedb_rappi.domain.usecase.api

import com.franco.moviedb_rappi.data.model.APIResponseTv
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class GetSearchTvUseCase(private val moviesRepository: MoviesRepository) {

    suspend fun execute(query: String, page : Int, language : String,) : Resource<APIResponseTv> {
        return moviesRepository.getSearchTv(query, page, language)
    }
}