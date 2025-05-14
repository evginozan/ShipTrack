package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}