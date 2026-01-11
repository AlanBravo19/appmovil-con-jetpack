package com.example.appmovil.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Clase de pedido para historial
data class OrderHistoryItem(
    val orderId: String,
    val products: List<String>,
    val total: String,
    val date: String
)

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Guardar usuario completo
    fun saveUser(
        name: String,
        email: String,
        password: String,
        address: String = "",
        phone: String = ""
    ) {
        prefs.edit()
            .putString("user_name", name)
            .putString("user_email", email)
            .putString("user_password", password)
            .putString("user_address", address)
            .putString("user_phone", phone)
            .putBoolean("is_logged_in", true)
            .apply()
    }

    // Actualizar datos de usuario
    fun updateUser(name: String, address: String, phone: String) {
        prefs.edit()
            .putString("user_name", name)
            .putString("user_address", address)
            .putString("user_phone", phone)
            .apply()
    }

    // Métodos de lectura de usuario
    fun getName() = prefs.getString("user_name", "")
    fun getEmail() = prefs.getString("user_email", "")
    fun getPassword() = prefs.getString("user_password", "")
    fun getAddress() = prefs.getString("user_address", "")
    fun getPhone() = prefs.getString("user_phone", "")

    fun isLoggedIn() = prefs.getBoolean("is_logged_in", false)

    fun logout() {
        prefs.edit().clear().apply()
    }

    // -------------------------------
    // HISTORIAL DE PEDIDOS
    // -------------------------------

    // Guardar un pedido
    fun saveOrder(order: OrderHistoryItem) {
        val currentOrders = getOrders().toMutableList()
        currentOrders.add(order)
        val json = gson.toJson(currentOrders)
        prefs.edit().putString("orders", json).apply()
    }

    // Obtener todos los pedidos
    fun getOrders(): List<OrderHistoryItem> {
        val json = prefs.getString("orders", null)
        return if (json != null) {
            val type = object : TypeToken<List<OrderHistoryItem>>() {}.type
            gson.fromJson(json, type)
        } else emptyList()
    }
}
