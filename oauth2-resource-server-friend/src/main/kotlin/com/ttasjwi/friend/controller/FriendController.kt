package com.ttasjwi.friend.controller

import com.ttasjwi.common.domain.Friend
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FriendController {

    @GetMapping("/friends")
    fun friends(): List<Friend> {
        val friend1 = Friend("friend1", 10, "male")
        val friend2 = Friend("friend2", 11, "female")

        return listOf(friend1, friend2)
    }
}
