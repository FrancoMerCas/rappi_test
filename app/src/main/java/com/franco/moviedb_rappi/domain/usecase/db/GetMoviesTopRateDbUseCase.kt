package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesTopRateDbUseCase(private val moviesRepository: MoviesRepository) {
    fun execute() : Flow<List<MovieTopRate>> {
        return moviesRepository.getAllMoviesTopRateDb()
    }
}