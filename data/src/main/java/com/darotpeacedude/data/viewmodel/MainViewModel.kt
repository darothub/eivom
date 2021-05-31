package com.darotpeacedude.data.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
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

    suspend fun allTheMovies():Array<Movie>{
        return repositoryImpl.allMovies()
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
