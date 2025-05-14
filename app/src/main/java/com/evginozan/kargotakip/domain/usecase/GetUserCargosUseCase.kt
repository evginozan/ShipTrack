package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.model.Cargo
import com.evginozan.kargotakip.domain.repository.AuthRepository
import com.evginozan.kargotakip.domain.repository.CargoRepository

class GetUserCargosUseCase(
    private val cargoRepository: CargoRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<List<Cargo>> {
        val user = authRepository.getCurrentUser() ?: return Result.failure(Exception("User not found"))
        return cargoRepository.getCargosByReceiverPhone(user.phone)
    }
}