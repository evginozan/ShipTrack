package com.evginozan.kargotakip.presentation.home

sealed class HomeEvent {
    object LoadCargos : HomeEvent()
    data class TrackingCodeChanged(val trackingCode: String) : HomeEvent()
    object TrackCargo : HomeEvent()
    data class NavigateToCargoDetail(val trackingCode: String) : HomeEvent()
    object NavigateToProfile : HomeEvent()
}