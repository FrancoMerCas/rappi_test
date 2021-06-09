package com.franco.moviedb_rappi.data.model
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = ["id"], unique = true )))
data class TvShowTopRate(
    @PrimaryKey(autoGenerate = true)
    val id_db: Int?,
    val id: Int?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
)