package com.franco.moviedb_rappi.domain.usecase.api

import com.franco.moviedb_rappi.data.model.APIResponseMovie
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class GetPopularMovieUseCase(private val moviesRepository: MoviesRepository) {

    suspend fun execute(language : String, page : Int) : Resource<APIResponseMovie> {
        return moviesRepository.getPopularMovie(language, page)
    }
}