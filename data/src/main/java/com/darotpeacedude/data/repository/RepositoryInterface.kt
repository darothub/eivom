package com.darotpeacedude.data.repository

import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.ResultList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    fun getLocalData(): Flow<Array<Movie>>
    suspend fun saveMovies(movies:List<Movie>)
    suspend fun getRemoteData(page:Int):ResultList
}