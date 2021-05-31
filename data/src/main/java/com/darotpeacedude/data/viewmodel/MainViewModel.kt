package com.darotpeacedude.data.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.Reader
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryInterface,
    private val pagingDataRepository: PagingSource<Int, Movie>,
    @ApplicationContext val appContext: Context
) : ViewModel() {
    private val TAG by lazy { this::class.qualifiedName!! }
    private val _netWorkStateFlow = MutableStateFlow(false)
    val netWorkStateFlow get() = _netWorkStateFlow

    init {
        networkMonitor()
    }

    fun remoteData() = Pager(PagingConfig(pageSize = 6)) {
        pagingDataRepository
    }.flow.cachedIn(viewModelScope)

    fun local()=Pager(
        PagingConfig(pageSize = 6),
        pagingSourceFactory = { repositoryImpl.local() }).flow.cachedIn(viewModelScope)

    suspend fun allTheMovies():Array<Movie>{
        return repositoryImpl.allTheMoviess()
    }

    @Synchronized
    private fun networkMonitor() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _netWorkStateFlow.value = true
            }

            override fun onLost(network: Network) {
                _netWorkStateFlow.value = false
            }
        }

        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(networkCallback)
            } else {
                val request: NetworkRequest = NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
                it.registerNetworkCallback(request, networkCallback)
            }
        }
    }

}
