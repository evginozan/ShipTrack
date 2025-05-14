package com.evginozan.kargotakip.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryCodeResponseDto(
    val deliveryCode: String
)