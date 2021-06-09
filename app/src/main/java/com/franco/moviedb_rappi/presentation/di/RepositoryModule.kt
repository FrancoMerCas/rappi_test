package com.franco.moviedb_rappi.presentation.di

import com.franco.moviedb_rappi.data.repository.MoviesRepositoryImpl
import com.franco.moviedb_rappi.data.repository.dataSource.MovieLocalDataSource
import com.franco.moviedb_rappi.data.repository.dataSource.MovieRemoteDataSource
import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        movieRemoteDataSource : MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ) : MoviesRepository {
        return MoviesRepositoryImpl(
            movieRemoteDataSource, movieLocalDataSource)
    }
}