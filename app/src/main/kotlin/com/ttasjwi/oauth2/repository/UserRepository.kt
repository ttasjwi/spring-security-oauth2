package com.ttasjwi.oauth2.repository

import com.ttasjwi.oauth2.model.User
import org.springframework.stereotype.Repository

/**
 * 우리 서비스에서 회원을 저장하고 관리하는 리포지토리
 */
@Repository
class UserRepository {

    private val users: MutableMap<String, User> = mutableMapOf()

    fun findByUserNameOrNull(username: String): User? {
        if (users.containsKey(username)) {
            return users[username]
        }
        return null
    }

    fun save(user: User) {
        if (users.containsKey(user.username)) {
            return
        }
        users[user.username] = user
    }

}
