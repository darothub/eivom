package com.darotpeacedude.core.di.modules.data.repository

import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.remote.NetworkService
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
        movieDao: MovieDao
    ): RepositoryInterface = RepositoryImpl(networkService, movieDao)
}
