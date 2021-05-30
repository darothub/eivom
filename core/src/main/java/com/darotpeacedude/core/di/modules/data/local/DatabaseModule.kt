package com.darotpeacedude.core.di.modules.data.local

import android.content.Context
import androidx.room.Room
import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context): MovieDatabase = Room.databaseBuilder(
        appContext,
        MovieDatabase::class.java, "moviedb"
    ).build()

    @Provides
    fun provideDao(appDatabase: MovieDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}
