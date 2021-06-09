package com.franco.moviedb_rappi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.franco.moviedb_rappi.data.model.TvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvDb(tvShow: List<TvShow>)

    @Query("SELECT * FROM TvShow ORDER BY popularity DESC")
    fun getAllTvDb(): Flow<List<TvShow>>

    @Query("SELECT * FROM TvShow WHERE name like :searchQuery || '%'")
    fun searchTvDataBase(searchQuery: String): Flow<List<TvShow>>
}