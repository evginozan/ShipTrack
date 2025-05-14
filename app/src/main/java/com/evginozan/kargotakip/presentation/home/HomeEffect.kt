package com.evginozan.kargotakip.presentation.home

sealed class HomeEffect {
    data class NavigateToCargoDetail(val trackingCode: String) : HomeEffect()
    object NavigateToProfile : HomeEffect()
}