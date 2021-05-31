package com.darotpeacedude.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.ResultList
import com.darotpeacedude.data.model.ErrorResponse
import com.darotpeacedude.data.remote.NetworkService
import com.darotpeacedude.data.repository.PagingDataRepository
import com.darotpeacedude.data.repository.RepositoryInterface
import com.darotpeacedude.data.utils.Parent
import com.darotpeacedude.data.utils.ResponseWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.Reader
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryInterface,
    private val networkService: NetworkService,
    private val pagingDataRepository: PagingSource<Int, Movie>
) : ViewModel() {


    private val _moviesFlow = MutableStateFlow(emptyList<Parent>())
    val moviesFlow: StateFlow<List<Parent>>
        get() = _moviesFlow
    private val _state = MutableStateFlow<ResponseWrapper<Parent>>(ResponseWrapper.Empty())
    val state: StateFlow<ResponseWrapper<Parent>>
        get() = _state

    private suspend fun getRemoteMovies(page: Int) = repositoryImpl.getRemoteData(page)
    fun allMovies() = repositoryImpl.allMovies()
    private fun getLocalMovies() = repositoryImpl.getLocalData()

    suspend fun saveMovies(movies: List<Movie>) {
        repositoryImpl.saveMovies(movies)
    }

    fun listData() = Pager(PagingConfig(pageSize = 6)) {
        pagingDataRepository
    }.flow.cachedIn(viewModelScope)


    fun getSingleSourceMovies() {

//        viewModelScope.launch {
//            getLocalMovies().collect {
//                if (it.isEmpty()) {
//                    try {
//
//                    } catch (e: HttpException) {
//                        onErrorStateFlow(e)
//                    }
//
//                } else {
//                    val newResult: Flow<PagingData<Movie>> = repositoryImpl.allMovies()
//                    _moviesFlow.value = it.toList()
//                }
//            }
//
//        }
    }

    @Synchronized
    suspend fun <T : Parent> onSuccessStateFlow(r: T) {
        _state.value = ResponseWrapper.Loading(message = "Loading...")
        val gson = Gson()
        val list = (r as ResultList).results.map { result ->
            val json = gson.toJson(result)
            gson.fromJson<Movie>(json, object : TypeToken<Movie>() {}.type)

        }
        _moviesFlow.value = list
        saveMovies(list)
        _state.value = ResponseWrapper.Success(r as Parent)
    }

    fun onErrorStateFlow(e: HttpException) {
        _state.value = ResponseWrapper.Loading(message = "Loading...")
        val errorbody = e.response()?.errorBody()?.charStream()
        val code = e.code()
        Log.e("Viewmodel Flowerror", "${e.response()}")
        val error = errorConverter(errorbody)

        _state.value = ResponseWrapper.Error(
            error?.statusMessage,
            code
        )
    }

    private fun errorConverter(
        errorbody: Reader?
    ): ErrorResponse? {
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        return gson.fromJson(errorbody, type)
    }

}