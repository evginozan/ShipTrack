package com.evginozan.kargotakip.presentation.cargo

sealed class DeliveryCodeEvent {
    object LoadDeliveryCode : DeliveryCodeEvent()
}