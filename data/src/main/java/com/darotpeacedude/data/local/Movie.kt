package com.darotpeacedude.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darotpeacedude.data.utils.Parent
import java.io.Serializable
@Entity
data class Movie (
    val adult: Boolean,
    @ColumnInfo(name="backdrop_path")
    val backdropPath: String,
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name="original_language")
    val originalLanguage: String,
    val overview: String,
    @ColumnInfo(name="poster_path")
    val posterPath: String,
    @ColumnInfo(name="release_date")
    val releaseDate: String,
    val title: String,
    @ColumnInfo(name="vote_average")
    val voteAverage: Long,
): Serializable, Parent