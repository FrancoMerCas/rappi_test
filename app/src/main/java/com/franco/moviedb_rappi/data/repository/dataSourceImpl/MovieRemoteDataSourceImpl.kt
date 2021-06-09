package com.franco.moviedb_rappi.data.repository.dataSourceImpl

import com.franco.moviedb_rappi.data.api.MovieDbAPIService
import com.franco.moviedb_rappi.data.model.*
import com.franco.moviedb_rappi.data.repository.dataSource.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
        private val movieDbAPIService: MovieDbAPIService
) : MovieRemoteDataSource {

    override suspend fun getPopularMovie(language : String, page : Int): Response<APIResponseMovie> {
        return movieDbAPIService.getPopularMovie(language, page)
    }

    override suspend fun getPopularTv(language : String, page : Int): Response<APIResponseTv> {
        return movieDbAPIService.getPopularTv(language, page)
    }

    override suspend fun getTopRateMovie(language: String, page: Int): Response<APIResponseMovieTopRate> {
        return movieDbAPIService.getTopRateMovie(language, page)
    }

    override suspend fun getTopRateTv(language: String, page: Int): Response<APIResponseTvShowTopRate> {
        return movieDbAPIService.getTopRateTv(language, page)
    }

    override suspend fun getSearchTv(query: String, page: Int, language: String): Response<APIResponseTv> {
        return movieDbAPIService.getSearchTv(query, page, language)
    }

    override suspend fun getSearchMovie(query: String, page: Int, language: String): Response<APIResponseMovie> {
        return movieDbAPIService.getSearchMovie(query, page, language)
    }
}