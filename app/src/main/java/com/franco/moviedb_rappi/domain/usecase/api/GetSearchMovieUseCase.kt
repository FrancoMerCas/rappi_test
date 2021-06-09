package com.franco.moviedb_rappi.domain.usecase.api

import com.franco.moviedb_rappi.data.model.APIResponseMovie
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class GetSearchMovieUseCase(private val moviesRepository: MoviesRepository) {

    suspend fun execute(query: String, page : Int, language : String,) : Resource<APIResponseMovie> {
        return moviesRepository.getSearchMovie(query, page, language)
    }
}