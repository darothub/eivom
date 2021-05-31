package com.darotpeacedude.data.repository

import com.darotpeacedude.data.local.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class RepositoryImplTest {
   lateinit var repositoryInterface: RepositoryInterface
   lateinit var fakePagingDataRepository: FakePagingDataRepository

    @Before
    fun setUp() {
        repositoryInterface = FakeRepository()
        fakePagingDataRepository = FakePagingDataRepository(repositoryInterface)
    }

    @Test
    fun testSaveMovies() = runBlocking {
        val listOfMovies = ArrayList<Movie>()
        val movie =  Movie(  false,
            "/Wwyuiwiw99w000",
            1,
            "English",
            "The legend is a nice movie",
            "/Wwyuiwiw99w000",

            "22-3-21",
            "Legends",
            2.0)
        listOfMovies.add(movie)
        repositoryInterface.saveMovies(listOfMovies)
        assert(repositoryInterface.listOfMovies.contains(movie))
    }

    @Test
    fun testGetRemoteData() = runBlocking {
        val gson = Gson()
        val remoteData = repositoryInterface.getRemoteData(1)
        val data = remoteData.results.map { result ->
            val json = gson.toJson(result)
            gson.fromJson<Movie>(json, object : TypeToken<Movie>() {}.type)
        }
        repositoryInterface.saveMovies(data)
        assert(repositoryInterface.listOfMovies.contains(data[0]))
    }

    @Test
    fun testAllLocalMovies()= runBlocking{
        testGetRemoteData()
        val movies = repositoryInterface.allMovies()
        assert(repositoryInterface.listOfMovies.contains(movies[0]))
    }
}