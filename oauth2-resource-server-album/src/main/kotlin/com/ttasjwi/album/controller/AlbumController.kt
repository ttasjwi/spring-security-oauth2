package com.ttasjwi.album.controller

import com.ttasjwi.album.domain.PhotoService
import com.ttasjwi.common.domain.Photo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AlbumController(
    private val photoService: PhotoService
) {

    @GetMapping("/photos")
    fun photos(): List<Photo> {
        return photoService.loadPhotos()
    }
}
