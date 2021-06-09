package com.franco.moviedb_rappi.data.repository.dataSourceImpl

import com.franco.moviedb_rappi.data.db.MovieDAO
import com.franco.moviedb_rappi.data.db.MovieTopRateDAO
import com.franco.moviedb_rappi.data.db.TvDAO
import com.franco.moviedb_rappi.data.db.TvTopRateDAO
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.model.TvShowTopRate
import com.franco.moviedb_rappi.data.repository.dataSource.MovieLocalDataSource
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(
    private val movieDAO: MovieDAO,
    private val movieTopRateDAO: MovieTopRateDAO,
    private val tvDAO: TvDAO,
    private val tvTopRateDAO: TvTopRateDAO
) : MovieLocalDataSource {
    override suspend fun insertMovieDb(movie: List<Movie>) {
        movieDAO.insertMovieDb(movie)
    }

    override suspend fun insertMovieTopRateDb(movieTopRate: List<MovieTopRate>) {
        movieTopRateDAO.insertMovieTopRateDb(movieTopRate)
    }

    override suspend fun insertTvDb(tvShow: List<TvShow>) {
        tvDAO.insertTvDb(tvShow)
    }

    override suspend fun insertTvTopRateDb(tvShowTopRate: List<TvShowTopRate>) {
        tvTopRateDAO.insertTvTopRateDb(tvShowTopRate)
    }

    override fun getAllMoviesDb(): Flow<List<Movie>> {
        return movieDAO.getAllMoviesDb()
    }

    override fun getAllTvDb(): Flow<List<TvShow>> {
        return tvDAO.getAllTvDb()
    }

    override fun getAllMoviesTopRateDb(): Flow<List<MovieTopRate>> {
        return movieTopRateDAO.getAllMoviesTopRateDb()
    }

    override fun getAllTvTopRateDb(): Flow<List<TvShowTopRate>> {
        return tvTopRateDAO.getAllTvTopRateDb()
    }

    override fun searchDataBase(searchQuery: String): Flow<List<Movie>> {
        return movieDAO.searchDataBase(searchQuery)
    }

    override fun searchTvDataBase(searchQuery: String): Flow<List<TvShow>> {
        return tvDAO.searchTvDataBase(searchQuery)
    }
}