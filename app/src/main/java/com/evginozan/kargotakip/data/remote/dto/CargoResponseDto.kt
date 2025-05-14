package com.evginozan.kargotakip.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CargoResponseDto(
    val id: Long,
    val trackingCode: String,
    val senderFirstName: String,
    val senderLastName: String,
    val senderPhone: String,
    val receiverFirstName: String,
    val receiverLastName: String,
    val receiverPhone: String,
    val destinationAddress: String,
    val weight: Double,
    val dimensions: String,
    val status: String,
    val senderBranch: String,
    val currentBranch: String,
    val destinationBranch: String,
    val createdAt: String,
    val updatedAt: String,
    val deliveredAt: String?,
    val history: List<String>
)