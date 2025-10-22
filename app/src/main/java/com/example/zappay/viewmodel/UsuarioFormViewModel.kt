package com.example.zappay.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zappay.repository.UsuarioRepository


class UsuarioFormViewModel : ViewModel() {

    var nombre by mutableStateOf("")
    var correo by mutableStateOf("")
    var edad by mutableStateOf("")
    var password by mutableStateOf("")
    var rut by mutableStateOf("")
    var aceptaTerminos by mutableStateOf(false)

    var errorNombre by mutableStateOf("")
    var errorCorreo by mutableStateOf("")
    var errorEdad by mutableStateOf("")
    var errorPassword by mutableStateOf("")
    var errorRut by mutableStateOf("")
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
        } else if (UsuarioRepository.usuarios.any { it.correo.equals(correo, ignoreCase = true) }) {
            errorCorreo = "Correo ya registrado"
            esValido = false
        }else{
            errorCorreo = ""
        }
        //validad edad

        if (edad.isBlank()){
            errorEdad= "La edad es obligatoria"
            esValido=false

        }else if (edad < "18"){
            errorEdad = "Debes ser mayor de edad"
            esValido= false
        }else {
            errorEdad= ""
        }
        //validad contraseña
        if (password.isBlank()){
            errorPassword= "la contraseña es obligatoria"
            esValido=false
        }else{
            errorPassword=""
        }
        //validar rut
        if (rut.isBlank()){
            errorRut= "El rut es obligatorio"
            esValido= false
        }else if (!rut.contains(".") ||!rut.contains(".") || !rut.contains("-")) {
            errorRut = "Rut inválido"
            esValido = false
        }else if (UsuarioRepository.usuarios.any { it.rut.equals(rut, ignoreCase = true) }) {
            errorRut = "Rut ya registrado"
            esValido = false
        } else {
            errorRut= ""
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