package com.darotpeacedude.core.di.modules.data.repository

import androidx.paging.PagingSource
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.local.MovieDatabase
import com.darotpeacedude.data.remote.NetworkService
import com.darotpeacedude.data.repository.PagingDataRepository
import com.darotpeacedude.data.repository.RepositoryImpl
import com.darotpeacedude.data.repository.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideRepositoryInterface(
        networkService: NetworkService,
        movieDatabase: MovieDatabase
    ): RepositoryInterface = RepositoryImpl(networkService, movieDatabase)

    @Provides
    @ActivityRetainedScoped
    fun providePagingRepository(
        networkService: NetworkService,
        movieDao: MovieDao
    ): PagingSource<Int, Movie> = PagingDataRepository(networkService, movieDao)
}
