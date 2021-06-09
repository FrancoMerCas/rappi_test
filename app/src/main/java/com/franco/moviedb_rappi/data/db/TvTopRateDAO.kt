package com.franco.moviedb_rappi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.franco.moviedb_rappi.data.model.TvShow
import com.franco.moviedb_rappi.data.model.TvShowTopRate
import kotlinx.coroutines.flow.Flow

@Dao
interface TvTopRateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvTopRateDb(tvShowTopRate: List<TvShowTopRate>)

    @SuppressWarnings
    @Query("SELECT * FROM TvShowTopRate")
    fun getAllTvTopRateDb(): Flow<List<TvShowTopRate>>
}