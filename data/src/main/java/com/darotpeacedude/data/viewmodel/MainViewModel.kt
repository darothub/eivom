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
import kotlinx.coroutines.flow.*
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
    private val TAG by lazy { this::class.qualifiedName!! }


    fun listData() = Pager(PagingConfig(pageSize = 6)) {
        pagingDataRepository
    }.flow.cachedIn(viewModelScope)

    fun local()=Pager(
        PagingConfig(pageSize = 6),
        pagingSourceFactory = { repositoryImpl.local() }).flow.cachedIn(viewModelScope)

    val allMovies = repositoryImpl.getLocalData()


}
