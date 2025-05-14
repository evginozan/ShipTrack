package com.evginozan.kargotakip.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evginozan.kargotakip.domain.usecase.GetCurrentUserUseCase
import com.evginozan.kargotakip.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadUser()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadUser -> {
                loadUser()
            }
            is ProfileEvent.Logout -> {
                logout()
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val user = getCurrentUserUseCase()

                if (user != null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        user = user
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "Kullanıcı bilgileri yüklenemedi"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Beklenmeyen bir hata oluştu"
                )
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                logoutUseCase()
                _state.value = _state.value.copy(
                    isLoading = false,
                    isLoggedOut = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Çıkış yapılırken bir hata oluştu"
                )
            }
        }
    }
}
