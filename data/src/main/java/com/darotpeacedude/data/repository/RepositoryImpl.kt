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

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDatabase.movieDao().saveMovies(movies)
    }

    override suspend fun getRemoteData(page: Int): ResultList {
        return networkService.getMovies(page=page)
    }



    override suspend fun allMovies(): Array<Movie> {
       return movieDatabase.movieDao().allTheMoviess()
    }





}