package com.evginozan.kargotakip.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.evginozan.kargotakip.domain.model.CargoStatus
import com.evginozan.kargotakip.presentation.theme.CargoAtDeliveryColor
import com.evginozan.kargotakip.presentation.theme.CargoAtSenderColor
import com.evginozan.kargotakip.presentation.theme.CargoAtTransferColor
import com.evginozan.kargotakip.presentation.theme.CargoDeliveredColor
import com.evginozan.kargotakip.presentation.theme.CargoInTransitColor
import com.evginozan.kargotakip.presentation.theme.CargoOutForDeliveryColor

@Composable
fun CargoStatusBadge(status: CargoStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        CargoStatus.AT_SENDER_BRANCH -> Triple(CargoAtSenderColor, Color.White, "Gönderici Şubede")
        CargoStatus.IN_TRANSIT -> Triple(CargoInTransitColor, Color.Black, "Taşımada")
        CargoStatus.AT_TRANSFER_CENTER -> Triple(CargoAtTransferColor, Color.White, "Aktarma Merkezinde")
        CargoStatus.AT_DELIVERY_BRANCH -> Triple(CargoAtDeliveryColor, Color.White, "Teslimat Şubesinde")
        CargoStatus.OUT_FOR_DELIVERY -> Triple(CargoOutForDeliveryColor, Color.Black, "Dağıtımda")
        CargoStatus.DELIVERED -> Triple(CargoDeliveredColor, Color.White, "Teslim Edildi")
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}