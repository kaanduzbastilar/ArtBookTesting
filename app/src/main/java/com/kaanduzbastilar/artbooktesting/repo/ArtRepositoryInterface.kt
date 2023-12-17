package com.kaanduzbastilar.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.kaanduzbastilar.artbooktesting.model.ImageResponse
import com.kaanduzbastilar.artbooktesting.roomdb.Art
import com.kaanduzbastilar.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}