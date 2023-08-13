package com.sumere.artbooktesting.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art (
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "artistname")
    var artistName: String,

    @ColumnInfo(name = "year")
    var year: Int,

    @ColumnInfo(name = "imageURL")
    var imageURL: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)