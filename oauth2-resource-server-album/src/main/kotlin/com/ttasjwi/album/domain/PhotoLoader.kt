package com.ttasjwi.album.domain

import com.ttasjwi.common.domain.Photo
import org.springframework.stereotype.Component

@Component
class PhotoLoader {

    fun loadPhotos(): List<Photo> {
        val photo1 = Photo(
            photoId = "1",
            photoTitle = "Photo1 Title",
            photoDescription = "Photo1 is Nice",
            userId = "user1"
        )
        val photo2 = Photo(
            photoId = "2",
            photoTitle = "Photo2 Title",
            photoDescription = "Photo2 is Beautiful",
            userId = "user2"
        )
        return listOf(photo1, photo2)
    }
}
