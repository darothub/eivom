package com.darotpeacedude.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.local.ResultList
import com.darotpeacedude.data.remote.NetworkService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class PagingDataRepository @Inject constructor(private val networkService: NetworkService, private val movieDao: MovieDao) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = networkService.getMovies(page = currentLoadingPageKey)
            val responseData = mutableListOf<Movie>()
            val gson = Gson()
            val data = response.results .map { result ->
                val json = gson.toJson(result)
                gson.fromJson<Movie>(json, object : TypeToken<Movie>() {}.type)
            }
            responseData.addAll(data)
            movieDao.saveMovies(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val loadResult = LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
            movieDao.saveMovies(loadResult.data)
            return loadResult
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}