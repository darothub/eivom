package com.darotpeacedude.data.repository

import androidx.paging.PagedList
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.ResultList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    fun getLocalData(): Flow<Array<Movie>>
    suspend fun saveMovies(movies:List<Movie>)
    suspend fun getRemoteData(page:Int):ResultList
    fun allMovies():Flow<PagingData<Movie>>

}