package com.franco.moviedb_rappi.domain.usecase.db

import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.domain.repository.MoviesRepository

class InsertTvDbUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(tvShow: List<TvShow>) = moviesRepository.insertTvDb(tvShow)
}