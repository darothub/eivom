package com.darotpeacedude.data.repository

import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.local.ResultList
import com.darotpeacedude.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val movieDao: MovieDao
):RepositoryInterface {
    override  fun getLocalData(): Flow<Array<Movie>> {
        return movieDao.getAllMovies()
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDao.saveMovies(movies)
    }

    override suspend fun getRemoteData(page: Int): ResultList {
        return networkService.getMovies(page=page)
    }

}