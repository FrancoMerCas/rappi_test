package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchDataBaseUseCase(private val moviesRepository: MoviesRepository) {
    fun execute(searchQuery : String) : Flow<List<Movie>> {
        return moviesRepository.searchDataBase(searchQuery)
    }
}