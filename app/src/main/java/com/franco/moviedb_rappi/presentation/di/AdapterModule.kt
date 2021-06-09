package com.franco.moviedb_rappi.presentation.di

import com.franco.moviedb_rappi.presentation.adapter.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideMovieAdapter() : MovieAdapter {
        return MovieAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchMovieAdapter() : SearchMovieAdapter {
        return SearchMovieAdapter()
    }

    @Singleton
    @Provides
    fun provideTvAdapter() : TvAdapter {
        return TvAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchTvAdapter() : SearchTvAdapter {
        return SearchTvAdapter()
    }

    @Singleton
    @Provides
    fun provideMovieTopRateAdapter() : MovieTopRateAdapter {
        return MovieTopRateAdapter()
    }

    @Singleton
    @Provides
    fun provideTvTopRateAdapter() : TvTopRateAdapter {
        return TvTopRateAdapter()
    }
}