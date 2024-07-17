package com.ttasjwi.album.controller

import com.ttasjwi.album.controller.dto.MyInfo
import com.ttasjwi.album.domain.MyInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MyInfoController(
    private val myInfoService: MyInfoService
) {

    @GetMapping("/myInfo")
    fun myInfo(): MyInfo {
        return myInfoService.loadMyInfo()
    }
}
