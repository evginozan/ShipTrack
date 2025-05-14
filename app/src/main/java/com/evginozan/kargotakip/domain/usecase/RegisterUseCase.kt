package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.model.UserRegistration
import com.evginozan.kargotakip.domain.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(registration: UserRegistration): Result<Boolean> {
        return authRepository.register(registration)
    }
}