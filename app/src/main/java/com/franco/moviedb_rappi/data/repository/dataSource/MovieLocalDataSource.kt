package com.franco.moviedb_rappi.data.repository.dataSource

import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.model.TvShowTopRate
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun insertMovieDb(movie: List<Movie>)
    suspend fun insertMovieTopRateDb(movieTopRate: List<MovieTopRate>)
    suspend fun insertTvDb(tvShow: List<TvShow>)
    suspend fun insertTvTopRateDb(tvShowTopRate: List<TvShowTopRate>)

    fun getAllMoviesDb() : Flow<List<Movie>>
    fun getAllTvDb() : Flow<List<TvShow>>
    fun getAllMoviesTopRateDb() : Flow<List<MovieTopRate>>
    fun getAllTvTopRateDb() : Flow<List<TvShowTopRate>>
    fun searchDataBase(searchQuery: String) : Flow<List<Movie>>
    fun searchTvDataBase(searchQuery: String) : Flow<List<TvShow>>
}