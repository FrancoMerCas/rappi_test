package com.franco.moviedb_rappi.presentation.di

import com.franco.moviedb_rappi.domain.repository.MoviesRepository
import com.franco.moviedb_rappi.domain.usecase.api.*
import com.franco.moviedb_rappi.domain.usecase.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPopularMovieUseCase(
        moviesRepository: MoviesRepository
    ) : GetPopularMovieUseCase {
        return GetPopularMovieUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetPopularTvUseCase(
        moviesRepository: MoviesRepository
    ) : GetPopularTvUseCase {
        return GetPopularTvUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetTopRateMovieUseCase(
        moviesRepository: MoviesRepository
    ) : GetTopRateMovieUseCase {
        return GetTopRateMovieUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetTopRateTvUseCase(
        moviesRepository: MoviesRepository
    ) : GetTopRateTvUseCase {
        return GetTopRateTvUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchTvUseCase(
        moviesRepository: MoviesRepository
    ) : GetSearchTvUseCase {
        return GetSearchTvUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchMovieUseCase(
            moviesRepository: MoviesRepository
    ) : GetSearchMovieUseCase {
        return GetSearchMovieUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetMoviesDbUseCase(
        moviesRepository: MoviesRepository
    ) : GetMoviesDbUseCase {
        return GetMoviesDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetTvDbUseCase(
        moviesRepository: MoviesRepository
    ) : GetTvDbUseCase {
        return GetTvDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideInsertMovieDbUseCase(
        moviesRepository: MoviesRepository
    ) : InsertMovieDbUseCase {
        return InsertMovieDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideInsertTvDbUseCase(
        moviesRepository: MoviesRepository
    ) : InsertTvDbUseCase {
        return InsertTvDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideInsertMovieTopRateDbUseCase(
        moviesRepository: MoviesRepository
    ) : InsertMovieTopRateDbUseCase {
        return InsertMovieTopRateDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideInsertTvTopRateDbUseCase(
            moviesRepository: MoviesRepository
    ) : InsertTvTopRateDbUseCase {
        return InsertTvTopRateDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetMoviesTopRateDbUseCase(
        moviesRepository: MoviesRepository
    ) : GetMoviesTopRateDbUseCase {
        return GetMoviesTopRateDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetTvShowTopRateDbUseCase(
        moviesRepository: MoviesRepository
    ) : GetTvShowTopRateDbUseCase {
        return GetTvShowTopRateDbUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideSearchDataBaseUseCaseUseCase(
            moviesRepository: MoviesRepository
    ) : SearchDataBaseUseCase {
        return SearchDataBaseUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideSearchTvDataBaseUseCaseUseCase(
            moviesRepository: MoviesRepository
    ) : SearchTvDataBaseUseCase {
        return SearchTvDataBaseUseCase(moviesRepository)
    }

}