package com.franco.moviedb_rappi.presentation.di

import android.app.Application
import androidx.room.Room
import com.franco.moviedb_rappi.data.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideMoviesDataBase(app : Application) : MovieDBDataBase {
        return Room.databaseBuilder(app, MovieDBDataBase::class.java, "movies_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDBDataBase: MovieDBDataBase) : MovieDAO {
        return movieDBDataBase.getMovieDAO()
    }

    @Singleton
    @Provides
    fun provideMovieTopRateDao(movieDBDataBase: MovieDBDataBase) : MovieTopRateDAO {
        return movieDBDataBase.getMovieTopRateDAO()
    }

    @Singleton
    @Provides
    fun providetvDao(movieDBDataBase: MovieDBDataBase) : TvDAO {
        return movieDBDataBase.getTvDAO()
    }

    @Singleton
    @Provides
    fun providetvTopRateDao(movieDBDataBase: MovieDBDataBase) : TvTopRateDAO {
        return movieDBDataBase.getTvTopRateDAO()
    }

}