package com.ttasjwi.resourceserver.controller

import com.ttasjwi.resourceserver.domain.Photo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/photos")
    fun photos(): List<Photo> {
        val photo1 = Photo(
            photoId="1",
            userId="user1",
            photoTitle="Photo 1 Title",
            photoDescription = "Photo 1 is nice"
        )
        val photo2 = Photo(
            photoId="2",
            userId="user2",
            photoTitle="Photo 2 Title",
            photoDescription = "Photo 2 is beautiful"
        )
        return listOf(photo1, photo2)
    }

    @GetMapping("/remotePhotos")
    fun remotePhotos(): List<Photo> {
        val photo1 = Photo(
            photoId="remotePhoto1",
            userId="Remote user1",
            photoTitle="Remote Photo 1 Title",
            photoDescription = "Photo 1 is nice"
        )
        val photo2 = Photo(
            photoId="2",
            userId="Remote user2",
            photoTitle="Remote Photo 2 Title",
            photoDescription = "Remote Photo 2 is beautiful"
        )
        return listOf(photo1, photo2)
    }
}
