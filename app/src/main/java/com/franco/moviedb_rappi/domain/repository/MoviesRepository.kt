package com.franco.moviedb_rappi.domain.repository

import com.franco.moviedb_rappi.data.model.*
import com.franco.moviedb_rappi.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovie(language : String, page : Int) : Resource<APIResponseMovie>
    suspend fun getPopularTv(language : String, page : Int) : Resource<APIResponseTv>
    suspend fun getSearchMovie(searchQuery : String, page: Int, language: String) : Resource<APIResponseMovie>
    suspend fun getSearchTv(searchQuery : String, page: Int, language: String) : Resource<APIResponseTv>
    suspend fun getTopRateMovie(language : String, page : Int) : Resource<APIResponseMovieTopRate>
    suspend fun getTopRateTv(language : String, page : Int) : Resource<APIResponseTvShowTopRate>

    suspend fun insertMovieDb(movie: List<Movie>)
    suspend fun insertTvDb(tvShow: List<TvShow>)
    suspend fun insertMovieTopRateDb(movieTopRate: List<MovieTopRate>)
    suspend fun insertTvTopRateDb(tvShowTopRate: List<TvShowTopRate>)
    fun getAllMoviesDb() : Flow<List<Movie>>
    fun getAllTvDb() : Flow<List<TvShow>>
    fun getAllMoviesTopRateDb() : Flow<List<MovieTopRate>>
    fun getAllTvTopRateDb() : Flow<List<TvShowTopRate>>
    fun searchDataBase(searchQuery: String) : Flow<List<Movie>>
    fun searchTvDataBase(searchQuery: String) : Flow<List<TvShow>>

}