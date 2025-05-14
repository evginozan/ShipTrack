package com.evginozan.kargotakip.presentation.cargo

data class DeliveryCodeState(
    val isLoading: Boolean = false,
    val deliveryCode: String? = null,
    val errorMessage: String? = null
)