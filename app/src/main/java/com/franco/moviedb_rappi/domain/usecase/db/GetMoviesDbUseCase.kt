package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesDbUseCase(private val moviesRepository: MoviesRepository) {

    fun execute() : Flow<List<Movie>> {
        return moviesRepository.getAllMoviesDb()
    }
}