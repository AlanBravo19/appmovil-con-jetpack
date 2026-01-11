package com.example.appmovil.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmovil.data.SessionManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

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
    session: SessionManager
) {
    val scrollState = rememberScrollState()

    // Guardamos automáticamente el pedido
    LaunchedEffect(Unit) {
        val order = com.example.appmovil.data.OrderHistoryItem(
            orderId = "PED-${System.currentTimeMillis()}",
            products = purchase.products,
            total = purchase.total,
            date = "04/12/2025" // puedes reemplazar por fecha real
        )
        session.saveOrder(order)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.builder()
            .target(purchase.location)
            .zoom(12f)
            .build()
    }

    val userName = session.getName()
    val userAddress = session.getAddress()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        if (!userName.isNullOrEmpty()) {
            Text("Nombre: $userName", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (!userAddress.isNullOrEmpty()) {
            Text("Dirección: $userAddress", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text("Último total pagado: ${purchase.total}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Productos comprados:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        purchase.products.forEach { product ->
            Text("• $product", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            GoogleMap(cameraPositionState = cameraPositionState)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
