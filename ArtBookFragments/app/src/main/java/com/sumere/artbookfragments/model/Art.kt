package com.sumere.artbookfragments.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Art(
    @ColumnInfo(name = "artname")
    val artName: String,
    @ColumnInfo(name = "artistname")
    val artistName: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "imageBitmap")
    val image:ByteArray): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}