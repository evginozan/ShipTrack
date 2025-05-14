package com.evginozan.kargotakip.presentation.profile

sealed class ProfileEvent {
    object LoadUser : ProfileEvent()
    object Logout : ProfileEvent()
}