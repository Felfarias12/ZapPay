package com.example.zappay.request

import com.google.gson.annotations.SerializedName
data class ContactoRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("Nombre") val nombre: String,
    @SerializedName("Correo") val correo: String,
    @SerializedName("Rut") val rut: String,
    @SerializedName("Saldo") var saldo: Double
)

