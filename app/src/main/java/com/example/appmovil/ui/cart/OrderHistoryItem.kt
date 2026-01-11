package com.example.appmovil.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appmovil.data.SessionManager

@Composable
fun OrderHistoryScreen(
    session: SessionManager,
    onBack: () -> Unit
) {
    val orders = session.getOrders()
    val userName = session.getName()
    val userAddress = session.getAddress()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) {
            Text("◀ Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!userName.isNullOrEmpty()) {
            Text("Usuario: $userName", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (!userAddress.isNullOrEmpty()) {
            Text("Dirección: $userAddress", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text("Historial de Compras", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(orders) { order ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Pedido ID: ${order.orderId}", style = MaterialTheme.typography.titleMedium)
                        Text("Fecha: ${order.date}", style = MaterialTheme.typography.bodyMedium)
                        Text("Total: ${order.total}", style = MaterialTheme.typography.bodyMedium)
                        Text("Productos:", style = MaterialTheme.typography.bodyMedium)
                        order.products.forEach { product ->
                            Text("• $product", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
