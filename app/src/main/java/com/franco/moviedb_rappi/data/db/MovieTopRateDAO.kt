package com.franco.moviedb_rappi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.franco.moviedb_rappi.data.model.Movie
import com.franco.moviedb_rappi.data.model.MovieTopRate
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTopRateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieTopRateDb(movieTopRate: List<MovieTopRate>)

    @Query("SELECT * FROM MovieTopRate")
    fun getAllMoviesTopRateDb(): Flow<List<MovieTopRate>>
}