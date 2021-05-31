package com.darotpeacedude.data.repository

import androidx.lifecycle.asFlow
import androidx.paging.*
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.local.MovieDatabase
import com.darotpeacedude.data.local.ResultList
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

    override fun allMovies(): Flow<PagingData<Movie>> = flow{
        Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = {PagingDataRepository(networkService, movieDatabase.movieDao())}
        )

    }

    override fun allTheMovies(): Array<Movie> {
        return movieDatabase.movieDao().allTheMovies()
    }

    override suspend fun allTheMoviess(): Array<Movie> {
       return movieDatabase.movieDao().allTheMoviess()
    }

    override fun local(): PagingSource<Int, Movie> = movieDatabase.movieDao().allMovies()




}