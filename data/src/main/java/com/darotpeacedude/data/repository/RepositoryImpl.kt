package com.darotpeacedude.data.repository

import androidx.lifecycle.asFlow
import androidx.paging.*
import com.darotpeacedude.data.local.*
import com.darotpeacedude.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val movieDatabase: MovieDatabase
):RepositoryInterface {
    override  fun getLocalData(): Flow<Array<Movie>> {
        return movieDatabase.movieDao().getAllMovies()
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDatabase.movieDao().saveMovies(movies)
    }

    override suspend fun getRemoteData(page: Int): ResultList {
        return networkService.getMovies(page=page)
    }

    override fun allMovies(): Flow<PagingData<Movie>>  {
       return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(networkService, movieDatabase),
            pagingSourceFactory = {movieDatabase.movieDao().allMovies()}
        ).flow

    }


}