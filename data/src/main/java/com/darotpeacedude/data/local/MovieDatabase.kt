package com.darotpeacedude.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 5, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
