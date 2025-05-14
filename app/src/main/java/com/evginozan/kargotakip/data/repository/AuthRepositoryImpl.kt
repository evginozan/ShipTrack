package com.evginozan.kargotakip.data.repository

import com.evginozan.kargotakip.data.local.UserPreferences
import com.evginozan.kargotakip.data.remote.KargoTakipApiService
import com.evginozan.kargotakip.data.remote.dto.LoginRequestDto
import com.evginozan.kargotakip.data.remote.dto.RegisterRequestDto
import com.evginozan.kargotakip.domain.model.User
import com.evginozan.kargotakip.domain.model.UserRegistration
import com.evginozan.kargotakip.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: KargoTakipApiService,
    private val userPreferences: UserPreferences
) : AuthRepository
{

    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            val response = apiService.login(LoginRequestDto(username, password))
            val user = User(
                id = response.id,
                username = response.username,
                email = response.email,
                fullName = response.fullName,
                phone = response.phone,
                token = response.token
            )
            userPreferences.saveToken(response.token)
            userPreferences.saveUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: UserRegistration): Result<Boolean> {
        return try {
            val request = RegisterRequestDto(
                username = user.username,
                password = user.password,
                email = user.email,
                fullName = user.fullName,
                phone = user.phone
            )
            apiService.register(request)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        userPreferences.clearUserData()
    }

    override suspend fun getCurrentUser(): User? {
        return userPreferences.getUser()
    }

    override suspend fun saveAuthToken(token: String) {
        userPreferences.saveToken(token)
    }

    override suspend fun getAuthToken(): String? {
        return userPreferences.getToken()
    }
}
