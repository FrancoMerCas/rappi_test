package com.franco.moviedb_rappi.presentation.di

import android.app.Application
import com.franco.moviedb_rappi.domain.usecase.api.*
import com.franco.moviedb_rappi.domain.usecase.db.*
import com.franco.moviedb_rappi.presentation.viewmodel.MovieViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        application: Application,
        getPopularMovieUseCase : GetPopularMovieUseCase,
        getPopularTvUseCase: GetPopularTvUseCase,
        getTopRateMovieUseCase: GetTopRateMovieUseCase,
        getTopRateTvUseCase: GetTopRateTvUseCase,
        getSearchMovieUseCase: GetSearchMovieUseCase,
        getSearchTvUseCase: GetSearchTvUseCase,
        getMoviesDbUseCase: GetMoviesDbUseCase,
        getTvDbUseCase: GetTvDbUseCase,
        insertMovieDbUseCase: InsertMovieDbUseCase,
        insertTvDbUseCase: InsertTvDbUseCase,
        getMoviesTopRateDbUseCase: GetMoviesTopRateDbUseCase,
        getTvShowTopRateDbUseCase: GetTvShowTopRateDbUseCase,
        insertMovieTopRateDbUseCase: InsertMovieTopRateDbUseCase,
        insertTvTopRateDbUseCase: InsertTvTopRateDbUseCase,
        searchDataBaseUseCase: SearchDataBaseUseCase,
        searchTvDataBaseUseCase: SearchTvDataBaseUseCase
    ) : MovieViewModelFactory {
        return MovieViewModelFactory(
            application,
                getPopularMovieUseCase,
                getPopularTvUseCase,
                getTopRateMovieUseCase,
                getTopRateTvUseCase,
                getSearchMovieUseCase,
                getSearchTvUseCase,
                getMoviesDbUseCase,
                getTvDbUseCase,
                insertMovieDbUseCase,
                insertTvDbUseCase,
                getMoviesTopRateDbUseCase,
                getTvShowTopRateDbUseCase,
                insertMovieTopRateDbUseCase,
                insertTvTopRateDbUseCase,
                searchDataBaseUseCase,
                searchTvDataBaseUseCase
        )
    }
}