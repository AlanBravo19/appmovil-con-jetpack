package com.example.appmovil.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmovil.data.SessionManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

// ✅ Clase con userId incluida
data class PurchaseInfo(
    val total: String,
    val products: List<String>,
    val location: LatLng,
    val userId: String
)

@Composable
fun PurchaseCompleteScreenCompose(
    navController: NavController,
    purchase: PurchaseInfo,
    session: SessionManager // 🔹 pasamos session para acceder a datos del usuario
) {
    val scrollState = rememberScrollState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.builder()
            .target(purchase.location)
            .zoom(12f)
            .build()
    }

    // Obtenemos datos del usuario desde session
    val userName = session.getName()
    val userAddress = session.getAddress()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // 🧑 Usuario
        if (!userName.isNullOrEmpty()) {
            Text(
                "Nombre: $userName",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (!userAddress.isNullOrEmpty()) {
            Text(
                "Dirección: $userAddress",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // 💰 Total pagado
        Text(
            "Último total pagado: ${purchase.total}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🛍 Productos
        Text(
            "Productos comprados:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))

        purchase.products.forEach { product ->
            Text("• $product", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🗺 Mapa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            GoogleMap(cameraPositionState = cameraPositionState)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
