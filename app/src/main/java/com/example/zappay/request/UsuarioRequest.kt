package com.example.zappay.request

data class UsuarioRequest(
    var Nombre: String,
    var Correo: String,
    var Edad: String,
    var Contrasenna: String,
    var Rut: String,
    var Saldo: Double    // ← DEBE SER var
)
