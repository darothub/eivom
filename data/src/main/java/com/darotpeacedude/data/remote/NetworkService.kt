package com.darotpeacedude.data.remote

import com.darotpeacedude.data.local.ResultList
import com.darotpeacedude.data.utils.Constant
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("popular")
    suspend fun getMovies(@Query("api_key") apiKey:String=Constant.API_KEY, @Query("page")page:Int):ResultList
}