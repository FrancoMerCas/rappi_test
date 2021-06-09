package com.franco.moviedb_rappi.presentation.di

import com.franco.moviedb_rappi.data.api.MovieDbAPIService
import com.franco.moviedb_rappi.data.repository.dataSource.MovieRemoteDataSource
import com.franco.moviedb_rappi.data.repository.dataSourceImpl.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(movieDbAPIService: MovieDbAPIService) : MovieRemoteDataSource{
        return MovieRemoteDataSourceImpl(movieDbAPIService)
    }
}