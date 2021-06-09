package com.franco.moviedb_rappi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.franco.moviedb_rappi.data.model.Movie
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieDb(movie: List<Movie>)

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    fun getAllMoviesDb(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE title like :searchQuery || '%'")
    fun searchDataBase(searchQuery: String): Flow<List<Movie>>
}