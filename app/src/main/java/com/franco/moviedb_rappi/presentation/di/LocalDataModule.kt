package com.franco.moviedb_rappi.presentation.di

import com.franco.moviedb_rappi.data.db.MovieDAO
import com.franco.moviedb_rappi.data.db.MovieTopRateDAO
import com.franco.moviedb_rappi.data.db.TvDAO
import com.franco.moviedb_rappi.data.db.TvTopRateDAO
import com.franco.moviedb_rappi.data.repository.dataSource.MovieLocalDataSource
import com.franco.moviedb_rappi.data.repository.dataSourceImpl.MovieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(movieDAO: MovieDAO,
                               movieTopRateDAO: MovieTopRateDAO,
                               tvDAO: TvDAO, tvTopRateDAO:
                               TvTopRateDAO) : MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDAO,
            movieTopRateDAO,
            tvDAO,
            tvTopRateDAO)
    }
}