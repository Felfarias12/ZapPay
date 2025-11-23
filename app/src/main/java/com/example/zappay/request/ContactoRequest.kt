package com.example.zappay.request

data class ContactoRequest(
    val Nombre: String,
    val Rut: String,
    val Correo: String,
    var Saldo: Double
)