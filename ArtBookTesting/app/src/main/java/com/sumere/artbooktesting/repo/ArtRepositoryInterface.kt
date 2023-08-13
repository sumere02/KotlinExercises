package com.sumere.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.sumere.artbooktesting.model.ImageResponse
import com.sumere.artbooktesting.roomdb.Art
import com.sumere.artbooktesting.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArt(): LiveData<List<Art>>
    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}