package com.example.zappay.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zappay.model.UsuarioForm
import com.example.zappay.model.UsuarioFormError

class UsuarioFormViewModel : ViewModel() {

    var nombre by mutableStateOf("")
    var correo by mutableStateOf("")
    var aceptaTerminos by mutableStateOf(false)

    var errorNombre by mutableStateOf("")
    var errorCorreo by mutableStateOf("")
    var errorTerminos by mutableStateOf("")

    fun validarFormulario(): Boolean {
        var esValido = true

        // Validar nombre
        if (nombre.isBlank()) {
            errorNombre = "El nombre es obligatorio"
            esValido = false
        } else {
            errorNombre = ""
        }

        // Validar correo
        if (correo.isBlank()) {
            errorCorreo = "El correo es obligatorio"
            esValido = false
        } else if (!correo.contains("@") || !correo.contains(".")) {
            errorCorreo = "Correo inválido"
            esValido = false
        } else {
            errorCorreo = ""
        }

        // Validar términos
        if (!aceptaTerminos) {
            errorTerminos = "Debes aceptar los términos"
            esValido = false
        } else {
            errorTerminos = ""
        }

        return esValido
    }

    fun limpiarFormulario() {
        nombre = ""
        correo = ""
        aceptaTerminos = false
        errorNombre = ""
        errorCorreo = ""
        errorTerminos = ""
    }
}