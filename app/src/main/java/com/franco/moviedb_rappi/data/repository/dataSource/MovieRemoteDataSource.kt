package com.franco.moviedb_rappi.data.repository.dataSource

import com.franco.moviedb_rappi.data.model.*
import retrofit2.Response

interface MovieRemoteDataSource {

    suspend fun getPopularMovie(language : String, page : Int) : Response<APIResponseMovie>
    suspend fun getPopularTv(language : String, page : Int) : Response<APIResponseTv>
    suspend fun getTopRateMovie(language : String, page : Int) : Response<APIResponseMovieTopRate>
    suspend fun getTopRateTv(language : String, page : Int) : Response<APIResponseTvShowTopRate>
    suspend fun getSearchTv(query : String, page: Int, language : String): Response<APIResponseTv>
    suspend fun getSearchMovie(query : String, page: Int, language : String): Response<APIResponseMovie>
}