package com.example.appmovil.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.net.URLDecoder

@Composable
fun OrderSummaryScreen(
    navController: NavController,
    total: String,                 // 🔹 String
    productsEncoded: String,
    userId: String
) {
    val productsDecoded = URLDecoder.decode(productsEncoded, StandardCharsets.UTF_8.toString())
    val products = productsDecoded.split("|").map {
        val parts = it.split(";")
        val name = parts.getOrNull(0) ?: ""
        val qty = parts.getOrNull(1) ?: "0"
        val price = parts.getOrNull(2) ?: "0.0"
        Triple(name, qty, price)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Resumen de la Compra")

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(products) { (name, qty, price) ->
                Text(
                    text = "$name x$qty — $${qty.toInt() * price.toDouble()}",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }

        Text(
            text = "Total: $total",
            modifier = Modifier.padding(vertical = 10.dp)
        )

        // 🔙 Volver
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Volver")
        }

        // 🟢 Finalizar compra
        Button(
            onClick = {
                val namesRaw = products.joinToString("|") { it.first }
                val namesEncoded = URLEncoder.encode(namesRaw, StandardCharsets.UTF_8.toString())
                navController.navigate("purchase_complete/$total/$namesEncoded/$userId")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text("Finalizar compra")
        }
    }
}
