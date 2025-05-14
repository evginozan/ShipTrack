package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.repository.CargoRepository

class GetDeliveryCodeUseCase(private val cargoRepository: CargoRepository) {
    suspend operator fun invoke(trackingCode: String): Result<String> {
        return cargoRepository.getDeliveryCode(trackingCode)
    }
}