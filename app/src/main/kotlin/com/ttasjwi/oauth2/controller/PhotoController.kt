package com.ttasjwi.oauth2.controller

import com.ttasjwi.oauth2.domain.Photo
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PhotoController {

    @GetMapping("/photos/1")
    fun photo1(): Photo {
        return Photo(
            userId="user1",
            photoId = "1",
            photoTitle = "Photo1",
            photoDescription = "이것은 photo 1 입니다",
        )
    }

    @GetMapping("/photos/2")
    @PreAuthorize("hasAuthority('SCOPE_photo')")
    fun photos2(): Photo {
        return Photo(
            userId="user1",
            photoId = "2",
            photoTitle = "Photo2",
            photoDescription = "이것은 photo 2 입니다",
        )
    }
}
