package com.evginozan.kargotakip.data.repository

import com.evginozan.kargotakip.data.remote.KargoTakipApiService
import com.evginozan.kargotakip.data.remote.dto.CargoResponseDto
import com.evginozan.kargotakip.domain.model.Cargo
import com.evginozan.kargotakip.domain.model.CargoStatus
import com.evginozan.kargotakip.domain.repository.CargoRepository

class CargoRepositoryImpl(private val apiService: KargoTakipApiService) : CargoRepository
{

    override suspend fun getCargoByTrackingCode(trackingCode: String): Result<Cargo> {
        return try {
            val response = apiService.getCargoByTrackingCode(trackingCode)
            Result.success(mapToCargo(response))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCargosBySenderPhone(phone: String): Result<List<Cargo>> {
        return try {
            val response = apiService.getCargosBySenderPhone(phone)
            Result.success(response.map { mapToCargo(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCargosByReceiverPhone(phone: String): Result<List<Cargo>> {
        return try {
            val response = apiService.getCargosByReceiverPhone(phone)
            Result.success(response.map { mapToCargo(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDeliveryCode(trackingCode: String): Result<String> {
        return try {
            val response = apiService.getDeliveryCode(trackingCode)
            Result.success(response.deliveryCode)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyDeliveryCode(trackingCode: String, deliveryCode: String): Result<Cargo> {
        return try {
            val response = apiService.verifyDeliveryCode(trackingCode, deliveryCode)
            Result.success(mapToCargo(response))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapToCargo(dto: CargoResponseDto): Cargo {
        return Cargo(
            id = dto.id,
            trackingCode = dto.trackingCode,
            senderName = "${dto.senderFirstName} ${dto.senderLastName}",
            receiverName = "${dto.receiverFirstName} ${dto.receiverLastName}",
            status = CargoStatus.valueOf(dto.status),
            weight = dto.weight,
            dimensions = dto.dimensions,
            senderBranch = dto.senderBranch,
            currentBranch = dto.currentBranch,
            destinationBranch = dto.destinationBranch,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt,
            deliveredAt = dto.deliveredAt,
            history = dto.history
        )
    }
}
