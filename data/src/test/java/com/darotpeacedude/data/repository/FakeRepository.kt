package com.darotpeacedude.data.repository

import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.Result
import com.darotpeacedude.data.local.ResultList

class FakeRepository : RepositoryInterface {
    override val listOfMovies = ArrayList<Movie>()
    private val remoteData = ResultList(1, arrayListOf(Result(
        false,
        "/Wwyuiwiw99w000",
        listOf(1, 2),
        1,
        "English",
        "Legends",
        "The legend is a nice movie",
        4.300,
        "/Wwyuiwiw99w000",
        "22-3-21",
        "Legends",
        false,
        2.0,
        111111,
    ), Result(
        false,
        "/Wwyuiwiw99w000",
        listOf(1, 2),
        1,
        "English",
        "Legends",
        "The legend is a nice movie",
        4.300,
        "/Wwyuiwiw99w000",
        "22-3-21",
        "Legends",
        false,
        2.0,
        111111,
    )))

    override suspend fun saveMovies(movies: List<Movie>) {
        listOfMovies.addAll(movies)
    }

    override suspend fun getRemoteData(page: Int): ResultList {
        return remoteData
    }

    override suspend fun allMovies(): Array<Movie> {
        return listOfMovies.toTypedArray()
    }
}