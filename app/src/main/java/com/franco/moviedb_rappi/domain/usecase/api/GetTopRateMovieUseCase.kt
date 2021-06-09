package com.franco.moviedb_rappi.domain.usecase.api

import com.franco.moviedb_rappi.data.model.APIResponseMovie
import com.franco.moviedb_rappi.data.model.APIResponseMovieTopRate
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class GetTopRateMovieUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(language : String, page : Int) : Resource<APIResponseMovieTopRate> {
        return moviesRepository.getTopRateMovie(language, page)
    }
}