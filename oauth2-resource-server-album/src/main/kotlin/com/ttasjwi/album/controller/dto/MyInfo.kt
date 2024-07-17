package com.ttasjwi.album.controller.dto

import com.ttasjwi.common.domain.Friend
import com.ttasjwi.common.domain.Photo

class MyInfo (
    val photos: List<Photo>,
    val friends: List<Friend>,
)
