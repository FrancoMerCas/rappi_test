package com.franco.moviedb_rappi.presentation.di

import com.franco.moviedb_rappi.BuildConfig
import com.franco.moviedb_rappi.data.api.MovieDbAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieAPIService(retrofit: Retrofit) : MovieDbAPIService {
        return retrofit.create(MovieDbAPIService::class.java)
    }
}