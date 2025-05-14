package com.evginozan.kargotakip.presentation.cargo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evginozan.kargotakip.domain.usecase.GetDeliveryCodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeliveryCodeViewModel(
    private val getDeliveryCodeUseCase: GetDeliveryCodeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val trackingCode: String = checkNotNull(savedStateHandle["trackingCode"])

    private val _state = MutableStateFlow(DeliveryCodeState())
    val state: StateFlow<DeliveryCodeState> = _state.asStateFlow()

    init {
        loadDeliveryCode()
    }

    fun onEvent(event: DeliveryCodeEvent) {
        when (event) {
            is DeliveryCodeEvent.LoadDeliveryCode -> {
                loadDeliveryCode()
            }
        }
    }

    private fun loadDeliveryCode() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val result = getDeliveryCodeUseCase(trackingCode)

                result.fold(
                    onSuccess = { deliveryCode ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            deliveryCode = deliveryCode
                        )
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Teslimat kodu yüklenemedi"
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