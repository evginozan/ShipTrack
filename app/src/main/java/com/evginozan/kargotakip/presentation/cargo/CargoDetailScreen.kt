package com.evginozan.kargotakip.presentation.cargo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evginozan.kargotakip.domain.model.CargoStatus
import com.evginozan.kargotakip.presentation.components.CargoStatusBadge
import com.evginozan.kargotakip.presentation.components.ErrorView
import com.evginozan.kargotakip.presentation.components.LoadingIndicator
import com.evginozan.kargotakip.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CargoDetailScreen(
    trackingCode: String,
    navController: NavController,
    viewModel: CargoDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CargoDetailEffect.NavigateToDeliveryCode -> {
                    navController.navigate(Screen.DeliveryCode.createRoute(effect.trackingCode))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kargo Detayı") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(CargoDetailEvent.RefreshCargo) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Yenile")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                LoadingIndicator()
            } else if (state.errorMessage != null) {
                ErrorView(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.onEvent(CargoDetailEvent.LoadCargo) }
                )
            } else if (state.cargo != null) {
                val cargo = state.cargo!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Kargo Başlık ve Durum
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Takip No: ${cargo.trackingCode}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                CargoStatusBadge(status = cargo.status)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (cargo.status == CargoStatus.OUT_FOR_DELIVERY) {
                                Button(
                                    onClick = { viewModel.onEvent(CargoDetailEvent.ViewDeliveryCode) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Teslimat Kodunu Görüntüle")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Kargo Bilgileri
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Kargo Bilgileri",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            CargoInfoRow("Gönderici", cargo.senderName)
                            CargoInfoRow("Alıcı", cargo.receiverName)
                            CargoInfoRow("Ağırlık", "${cargo.weight} kg")
                            CargoInfoRow("Boyutlar", cargo.dimensions)
                            CargoInfoRow("Çıkış Şubesi", cargo.senderBranch)
                            CargoInfoRow("Şu Anki Konum", cargo.currentBranch)
                            CargoInfoRow("Varış Şubesi", cargo.destinationBranch)
                            CargoInfoRow("Oluşturulma", cargo.createdAt)
                            CargoInfoRow("Son Güncelleme", cargo.updatedAt)

                            if (cargo.deliveredAt != null) {
                                CargoInfoRow("Teslim Edilme", cargo.deliveredAt)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Kargo Geçmişi
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Kargo Geçmişi",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            cargo.history.forEach { historyItem ->
                                Text(
                                    text = historyItem,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )

                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CargoInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Divider(modifier = Modifier.padding(vertical = 4.dp))
}