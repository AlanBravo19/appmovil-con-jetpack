package com.example.appmovil.ui.cart

import com.google.android.gms.maps.model.LatLng

data class PurchaseData(
    val total: String,
    val products: List<String>,
    val location: LatLng,
    val userId: String
)
