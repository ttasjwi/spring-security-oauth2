package com.ttasjwi.album.domain

import com.ttasjwi.album.controller.dto.MyInfo
import org.springframework.stereotype.Service

@Service
class MyInfoService(
    private val myInfoLoader: MyInfoLoader,
) {

    fun loadMyInfo(): MyInfo {
        return myInfoLoader.loadMyInfo()
    }
}
