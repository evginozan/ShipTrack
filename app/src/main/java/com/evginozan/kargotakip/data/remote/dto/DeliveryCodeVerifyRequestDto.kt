package com.evginozan.kargotakip.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryCodeVerifyRequestDto(
    val deliveryCode: String
)