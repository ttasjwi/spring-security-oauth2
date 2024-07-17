package com.ttasjwi.album.domain

import com.ttasjwi.album.controller.dto.MyInfo
import org.springframework.stereotype.Component

@Component
class MyInfoLoader(
    private val friendClient: FriendClient,
    private val photoLoader: PhotoLoader,
) {

    fun loadMyInfo(): MyInfo {
        val photos = photoLoader.loadPhotos()
        val friends = friendClient.loadFriends()
        return MyInfo(photos, friends)
    }
}
