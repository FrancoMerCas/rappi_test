package com.franco.moviedb_rappi.data.repository

import com.franco.moviedb_rappi.data.model.*
import com.franco.moviedb_rappi.data.repository.dataSource.MovieLocalDataSource
import com.franco.moviedb_rappi.data.repository.dataSource.MovieRemoteDataSource
import com.franco.moviedb_rappi.data.util.Resource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MoviesRepositoryImpl(
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val movieLocalDataSource: MovieLocalDataSource
) : MoviesRepository {

    fun responseToResourceTv(response : Response<APIResponseTv>) : Resource<APIResponseTv> {
        if(response.isSuccessful) {
            response.body()?.let {  result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun responseToResourceMovie(response : Response<APIResponseMovie>) : Resource<APIResponseMovie> {
        if(response.isSuccessful) {
            response.body()?.let {  result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun responseToResourceMovieTopRate(response : Response<APIResponseMovieTopRate>) : Resource<APIResponseMovieTopRate>{
        if(response.isSuccessful) {
            response.body()?.let {  result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun responseToResourceTvShowTopRate(response : Response<APIResponseTvShowTopRate>) : Resource<APIResponseTvShowTopRate>{
        if(response.isSuccessful) {
            response.body()?.let {  result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getPopularMovie(language : String, page : Int): Resource<APIResponseMovie> {
        return responseToResourceMovie(movieRemoteDataSource.getPopularMovie(language, page))
    }

    override suspend fun getPopularTv(language : String, page : Int): Resource<APIResponseTv> {
        return responseToResourceTv(movieRemoteDataSource.getPopularTv(language, page))
    }

    override suspend fun getSearchMovie(searchQuery: String, page: Int, language: String): Resource<APIResponseMovie> {
        return responseToResourceMovie(movieRemoteDataSource.getSearchMovie(searchQuery, page, language))
    }

    override suspend fun getSearchTv(searchQuery: String, page: Int, language: String): Resource<APIResponseTv> {
        return responseToResourceTv(movieRemoteDataSource.getSearchTv(searchQuery, page, language))
    }

    override suspend fun getTopRateMovie(language : String, page : Int): Resource<APIResponseMovieTopRate> {
        return responseToResourceMovieTopRate(movieRemoteDataSource.getTopRateMovie(language, page))
    }

    override suspend fun getTopRateTv(language : String, page : Int): Resource<APIResponseTvShowTopRate> {
        return responseToResourceTvShowTopRate(movieRemoteDataSource.getTopRateTv(language,page))
    }

    override suspend fun insertMovieDb(movie: List<Movie>) {
        movieLocalDataSource.insertMovieDb(movie)
    }

    override suspend fun insertTvDb(tvShow: List<TvShow>) {
        movieLocalDataSource.insertTvDb(tvShow)
    }

    override suspend fun insertMovieTopRateDb(movieTopRate: List<MovieTopRate>) {
        movieLocalDataSource.insertMovieTopRateDb(movieTopRate)
    }

    override suspend fun insertTvTopRateDb(tvShowTopRate: List<TvShowTopRate>) {
        movieLocalDataSource.insertTvTopRateDb(tvShowTopRate)
    }

    override fun getAllMoviesDb(): Flow<List<Movie>> {
        return movieLocalDataSource.getAllMoviesDb()
    }

    override fun getAllTvDb(): Flow<List<TvShow>> {
        return movieLocalDataSource.getAllTvDb()
    }

    override fun getAllMoviesTopRateDb(): Flow<List<MovieTopRate>> {
        return movieLocalDataSource.getAllMoviesTopRateDb()
    }

    override fun getAllTvTopRateDb(): Flow<List<TvShowTopRate>> {
        return movieLocalDataSource.getAllTvTopRateDb()
    }

    override fun searchDataBase(searchQuery: String): Flow<List<Movie>> {
        return movieLocalDataSource.searchDataBase(searchQuery)
    }

    override fun searchTvDataBase(searchQuery: String): Flow<List<TvShow>> {
        return movieLocalDataSource.searchTvDataBase(searchQuery)
    }
}