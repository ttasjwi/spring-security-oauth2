package com.ttasjwi.album.domain

import com.ttasjwi.common.domain.Photo
import org.springframework.stereotype.Service

@Service
class PhotoService(
    private val photoLoader: PhotoLoader,
) {

    fun loadPhotos(): List<Photo> {
        return photoLoader.loadPhotos()
    }
}
