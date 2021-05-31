package com.darotpeacedude.data.local

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<Array<Movie>>

    @Query("SELECT * FROM movie")
    fun allTheMovies():Array<Movie>

    @Query("SELECT * FROM movie")
    fun allMovies(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies:List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun clearMovies()
}