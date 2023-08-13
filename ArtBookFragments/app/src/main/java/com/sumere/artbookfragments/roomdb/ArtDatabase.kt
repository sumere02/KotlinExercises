package com.sumere.artbookfragments.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sumere.artbookfragments.model.Art

@Database(entities = arrayOf(Art::class), version = 1)
abstract class ArtDatabase: RoomDatabase() {
    abstract fun artDao(): ArtDao
}