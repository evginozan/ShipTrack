package com.evginozan.kargotakip.domain.repository

import com.evginozan.kargotakip.domain.model.User
import com.evginozan.kargotakip.domain.model.UserRegistration

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun register(user: UserRegistration): Result<Boolean>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): String?
}