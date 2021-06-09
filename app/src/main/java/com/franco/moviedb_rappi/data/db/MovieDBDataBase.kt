package com.franco.moviedb_rappi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.MovieTopRate
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.model.TvShowTopRate

@Database(
    entities = [Movie::class, TvShow::class, MovieTopRate::class, TvShowTopRate::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDBDataBase : RoomDatabase(){
    abstract fun getMovieDAO() : MovieDAO
    abstract fun getTvDAO() : TvDAO
    abstract fun getMovieTopRateDAO() : MovieTopRateDAO
    abstract fun getTvTopRateDAO() : TvTopRateDAO
}