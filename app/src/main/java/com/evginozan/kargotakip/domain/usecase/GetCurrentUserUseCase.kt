package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.model.User
import com.evginozan.kargotakip.domain.repository.AuthRepository

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): User? {
        return authRepository.getCurrentUser()
    }
}