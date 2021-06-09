package com.franco.moviedb_rappi.data.repository

import com.franco.moviedb_rappi.data.model.*
import com.franco.moviedb_rappi.data.util.Resource
import retrofit2.Response

class ResourceToResponse {
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

    fun responseToResourceTvTopRate(response : Response<APIResponseTvShowTopRate>) : Resource<APIResponseTvShowTopRate>{
        if(response.isSuccessful) {
            response.body()?.let {  result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}