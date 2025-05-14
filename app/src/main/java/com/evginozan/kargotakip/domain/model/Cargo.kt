package com.evginozan.kargotakip.domain.model

data class Cargo(
    val id: Long,
    val trackingCode: String,
    val senderName: String,
    val receiverName: String,
    val status: CargoStatus,
    val weight: Double,
    val dimensions: String,
    val senderBranch: String,
    val currentBranch: String,
    val destinationBranch: String,
    val createdAt: String,
    val updatedAt: String,
    val deliveredAt: String?,
    val history: List<String>
)