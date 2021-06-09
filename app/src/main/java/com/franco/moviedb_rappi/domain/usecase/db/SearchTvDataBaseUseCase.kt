package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchTvDataBaseUseCase(private val moviesRepository: MoviesRepository) {
    fun execute(searchQuery : String) : Flow<List<TvShow>> {
        return moviesRepository.searchTvDataBase(searchQuery)
    }
}