package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class InsertMovieTopRateDbUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(movieTopRate: List<MovieTopRate>) = moviesRepository.insertMovieTopRateDb(movieTopRate)
}