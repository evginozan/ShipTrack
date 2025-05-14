package com.evginozan.kargotakip.presentation.cargo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evginozan.kargotakip.domain.model.CargoStatus
import com.evginozan.kargotakip.domain.usecase.GetCargoByTrackingCodeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CargoDetailViewModel(
    private val getCargoByTrackingCodeUseCase: GetCargoByTrackingCodeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val trackingCode: String = checkNotNull(savedStateHandle["trackingCode"])

    private val _state = MutableStateFlow(CargoDetailState())
    val state: StateFlow<CargoDetailState> = _state.asStateFlow()

    private val _effect = Channel<CargoDetailEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadCargo()
    }

    fun onEvent(event: CargoDetailEvent) {
        when (event) {
            is CargoDetailEvent.LoadCargo -> {
                loadCargo()
            }
            is CargoDetailEvent.RefreshCargo -> {
                loadCargo()
            }
            is CargoDetailEvent.ViewDeliveryCode -> {
                viewDeliveryCode()
            }
        }
    }

    private fun loadCargo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val result = getCargoByTrackingCodeUseCase(trackingCode)

                result.fold(
                    onSuccess = { cargo ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            cargo = cargo
                        )
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Kargo yüklenemedi"
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

    private fun viewDeliveryCode() {
        val cargo = _state.value.cargo ?: return

        if (cargo.status == CargoStatus.OUT_FOR_DELIVERY) {
            viewModelScope.launch {
                _effect.send(CargoDetailEffect.NavigateToDeliveryCode(trackingCode))
            }
        } else {
            viewModelScope.launch {
                _state.value = _state.value.copy(
                    errorMessage = "Kargo henüz dağıtıma çıkmamış, teslimat kodu mevcut değil."
                )
            }
        }
    }
}
