package com.franco.moviedb_rappi.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.franco.moviedb_rappi.domain.usecase.api.*
import com.franco.moviedb_rappi.domain.usecase.db.*

class MovieViewModelFactory(
    private val app : Application,
    private val getPopularMovieUseCase : GetPopularMovieUseCase,
    private val getPopularTvUseCase: GetPopularTvUseCase,
    private val getTopRateMovieUseCase: GetTopRateMovieUseCase,
    private val getTopRateTvUseCase: GetTopRateTvUseCase,
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
    private val getSearchTvUseCase: GetSearchTvUseCase,
    private val getMoviesDbUseCase: GetMoviesDbUseCase,
    private val getTvDbUseCase: GetTvDbUseCase,
    private val insertMovieDbUseCase: InsertMovieDbUseCase,
    private val insertTvDbUseCase: InsertTvDbUseCase,
    private val getMoviesTopRateDbUseCase: GetMoviesTopRateDbUseCase,
    private val getTvShowTopRateDbUseCase: GetTvShowTopRateDbUseCase,
    private val insertMovieTopRateDbUseCase: InsertMovieTopRateDbUseCase,
    private val insertTvTopRateDbUseCase: InsertTvTopRateDbUseCase,
    private val searchDataBaseUseCase: SearchDataBaseUseCase,
    private val searchTvDataBaseUseCase: SearchTvDataBaseUseCase
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(
                app,
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
        ) as T
    }
}