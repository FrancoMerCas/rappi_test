package com.franco.moviedb_rappi.data.api

import com.franco.moviedb_rappi.BuildConfig
import com.franco.moviedb_rappi.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbAPIService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("language")
        language:String,
        @Query("page")
        page:Int,
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY,
    ) : Response<APIResponseMovie>

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("language")
        language:String,
        @Query("page")
        page:Int,
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY
    ) : Response<APIResponseTv>

    @GET("movie/top_rated")
    suspend fun getTopRateMovie(
        @Query("language")
        language:String,
        @Query("page")
        page:Int,
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY,
    ) : Response<APIResponseMovieTopRate>

    @GET("tv/top_rated")
    suspend fun getTopRateTv(
        @Query("language")
        language:String,
        @Query("page")
        page:Int,
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY
    ) : Response<APIResponseTvShowTopRate>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("query")
        query:String,
        @Query("page")
        page:Int,
        @Query("language")
        language:String,
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY,
    ) : Response<APIResponseMovie>

    @GET("search/tv")
    suspend fun getSearchTv(
        @Query("query")
        query:String,
        @Query("page")
        page:Int,
        @Query("language")
        language:String,
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY,
    ) : Response<APIResponseTv>
}