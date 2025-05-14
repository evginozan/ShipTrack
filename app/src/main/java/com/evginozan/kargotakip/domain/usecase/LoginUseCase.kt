package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.model.User
import com.evginozan.kargotakip.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.login(username, password)
    }
}