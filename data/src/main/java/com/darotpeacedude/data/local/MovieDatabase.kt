package com.darotpeacedude.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}