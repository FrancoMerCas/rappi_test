package com.franco.moviedb_rappi.domain.usecase.api

import com.franco.moviedb_rappi.data.model.APIResponseTv
import com.franco.moviedb_rappi.data.model.APIResponseTvShowTopRate
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class GetTopRateTvUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(language : String, page : Int) : Resource<APIResponseTvShowTopRate> {
        return moviesRepository.getTopRateTv(language, page)
    }
}