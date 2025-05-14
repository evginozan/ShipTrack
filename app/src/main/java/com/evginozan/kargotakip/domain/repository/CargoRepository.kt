package com.evginozan.kargotakip.domain.repository

import com.evginozan.kargotakip.domain.model.Cargo

interface CargoRepository {
    suspend fun getCargoByTrackingCode(trackingCode: String): Result<Cargo>
    suspend fun getCargosBySenderPhone(phone: String): Result<List<Cargo>>
    suspend fun getCargosByReceiverPhone(phone: String): Result<List<Cargo>>
    suspend fun getDeliveryCode(trackingCode: String): Result<String>
    suspend fun verifyDeliveryCode(trackingCode: String, deliveryCode: String): Result<Cargo>
}