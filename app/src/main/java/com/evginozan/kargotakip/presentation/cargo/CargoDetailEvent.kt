package com.evginozan.kargotakip.presentation.cargo

sealed class CargoDetailEvent {
    object LoadCargo : CargoDetailEvent()
    object RefreshCargo : CargoDetailEvent()
    object ViewDeliveryCode : CargoDetailEvent()
}