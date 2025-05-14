package com.evginozan.kargotakip.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evginozan.kargotakip.domain.usecase.GetCargoByTrackingCodeUseCase
import com.evginozan.kargotakip.domain.usecase.GetCurrentUserUseCase
import com.evginozan.kargotakip.domain.usecase.GetUserCargosUseCase
import com.evginozan.kargotakip.domain.usecase.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserCargosUseCase: GetUserCargosUseCase,
    private val getCargoByTrackingCodeUseCase: GetCargoByTrackingCodeUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadUser()
        loadCargos()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = getCurrentUserUseCase()
            _state.value = _state.value.copy(user = user)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadCargos -> {
                loadCargos()
            }
            is HomeEvent.TrackingCodeChanged -> {
                _state.value = _state.value.copy(trackingCode = event.trackingCode)
            }
            is HomeEvent.TrackCargo -> {
                trackCargo()
            }
            is HomeEvent.NavigateToCargoDetail -> {
                viewModelScope.launch {
                    _effect.send(HomeEffect.NavigateToCargoDetail(event.trackingCode))
                }
            }
            is HomeEvent.NavigateToProfile -> {
                viewModelScope.launch {
                    _effect.send(HomeEffect.NavigateToProfile)
                }
            }
        }
    }

    private fun loadCargos() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val result = getUserCargosUseCase()

                result.fold(
                    onSuccess = { cargos ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            cargos = cargos
                        )
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Kargolar yüklenemedi"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Beklenmeyen bir hata oluştu"
                )
            }
        }
    }

    private fun trackCargo() {
        val trackingCode = _state.value.trackingCode.trim()

        if (trackingCode.isEmpty()) {
            _state.value = _state.value.copy(
                errorMessage = "Takip kodu giriniz"
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val result = getCargoByTrackingCodeUseCase(trackingCode)

                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(isLoading = false)
                        _effect.send(HomeEffect.NavigateToCargoDetail(trackingCode))
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Kargo bulunamadı"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Beklenmeyen bir hata oluştu"
                )
            }
        }
    }
}