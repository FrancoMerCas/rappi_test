package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.model.TvShowTopRate
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetTvShowTopRateDbUseCase(private val moviesRepository: MoviesRepository) {
    fun execute() : Flow<List<TvShowTopRate>> {
        return moviesRepository.getAllTvTopRateDb()
    }
}