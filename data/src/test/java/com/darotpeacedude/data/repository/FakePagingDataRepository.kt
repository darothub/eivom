package com.darotpeacedude.data.repository

import androidx.paging.PagingSource
import com.darotpeacedude.data.local.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FakePagingDataRepository(private val fakeRepository: RepositoryInterface) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = fakeRepository.getRemoteData(page = currentLoadingPageKey)
            val responseData = mutableListOf<Movie>()
            val gson = Gson()
            val data = response.results .map { result ->
                val json = gson.toJson(result)
                gson.fromJson<Movie>(json, object : TypeToken<Movie>() {}.type)
            }
            responseData.addAll(data)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val loadResult = LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
            fakeRepository.saveMovies(loadResult.data)
            return loadResult
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}