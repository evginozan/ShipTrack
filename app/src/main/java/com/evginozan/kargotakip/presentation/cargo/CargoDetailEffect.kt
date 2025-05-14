package com.evginozan.kargotakip.presentation.cargo

sealed class CargoDetailEffect {
    data class NavigateToDeliveryCode(val trackingCode: String) : CargoDetailEffect()
}