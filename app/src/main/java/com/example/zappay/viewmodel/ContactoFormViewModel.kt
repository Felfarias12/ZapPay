package com.example.zappay.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zappay.repository.UsuarioRepository

class ContactoFormViewModel : ViewModel()  {

    var id by mutableStateOf(1)
    var createdAt by mutableStateOf(0L)
    var nombre by mutableStateOf("")
    var rut by mutableStateOf("")
    var correo by mutableStateOf("")
    var saldo by mutableStateOf(0.0)

    var errorNombre by mutableStateOf("")
    var errorRut by mutableStateOf("")
    var errorCorreo by mutableStateOf("")

    fun ValidarContacto(): Boolean {

        var esValido = true

        if (nombre.isBlank()) {
            errorNombre = "El nombre es obligatorio"
            esValido = false
        } else if (nombre.length < 2) {
            errorNombre = "El nombre debe tener al menos 2 caracteres"
            esValido = false
        } else {
            errorNombre = ""
        }

        if (rut.isBlank()) {
            errorRut = "El rut es obligatorio"
            esValido = false
        } else if (!rut.contains(".") || !rut.contains("-")) {
            errorRut = "Rut inválido. Ejemplo: 12.345.678-9"
            esValido = false
        } else {
            errorRut = ""
        }

        if (correo.isBlank()) {
            errorCorreo = "El correo es obligatorio"
            esValido = false
        } else if (!correo.contains("@") || !correo.contains(".")) {
            errorCorreo = "Correo inválido"
            esValido = false
        } else if (UsuarioRepository.usuarios.any { it.correo.equals(correo, ignoreCase = true) }) {
            errorCorreo = "Correo ya registrado"
            esValido = false
        }else{
            errorCorreo = ""
        }

        return esValido
    }
    fun limpiarContacto() {
        nombre = ""
        rut= ""
        correo = ""
        errorNombre = ""
        errorRut= ""
        errorCorreo = ""
    }
}