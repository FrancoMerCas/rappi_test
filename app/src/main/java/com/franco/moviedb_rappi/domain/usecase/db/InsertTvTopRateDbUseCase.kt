package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.TvShowTopRate
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class InsertTvTopRateDbUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(tvShowTopRate: List<TvShowTopRate>) = moviesRepository.insertTvTopRateDb(tvShowTopRate)
}