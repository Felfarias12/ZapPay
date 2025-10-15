package com.example.zappay.model

data class UsuarioFormulario(
    var nombre: String = "",
    var correo: String = "",
    var edad: String = "",
    var terminos: Boolean = false
)