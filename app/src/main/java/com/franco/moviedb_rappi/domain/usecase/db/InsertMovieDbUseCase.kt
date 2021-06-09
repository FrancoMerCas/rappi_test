package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class InsertMovieDbUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(movie: List<Movie>) = moviesRepository.insertMovieDb(movie)
}
