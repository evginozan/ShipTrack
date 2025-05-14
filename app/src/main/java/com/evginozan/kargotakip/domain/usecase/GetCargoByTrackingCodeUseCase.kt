package com.evginozan.kargotakip.domain.usecase

import com.evginozan.kargotakip.domain.model.Cargo
import com.evginozan.kargotakip.domain.repository.CargoRepository

class GetCargoByTrackingCodeUseCase(private val cargoRepository: CargoRepository) {
    suspend operator fun invoke(trackingCode: String): Result<Cargo> {
        return cargoRepository.getCargoByTrackingCode(trackingCode)
    }
}