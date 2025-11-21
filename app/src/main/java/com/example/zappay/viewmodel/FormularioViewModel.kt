package com.example.zappay.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zappay.model.Usuario
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.repository.UsuarioRepository
import com.example.zappay.request.UsuarioRequest



class FormularioViewModel : ViewModel() {
    var password by mutableStateOf("")
    var rut by mutableStateOf("")
    var errorPassword by mutableStateOf("")
    var errorRut by mutableStateOf("")


    fun validarUsuario(): Boolean {
        var esValido = true

        //validar rut
        if (rut.isBlank()) {
            errorRut = "El rut es obligatorio"
            esValido = false
        } else if (!rut.contains(".") || !rut.contains(".") || !rut.contains("-")) {
            errorRut = "Rut inválido"
            esValido = false
        } else if (UsuarioRepository.usuarios.none { it.rut.equals(rut, ignoreCase = true) }) {
            errorRut = "Rut no registrado"
            esValido = false
        } else {
            errorRut = ""
        }


        //validad contraseña
        if (password.isBlank()) {
            errorPassword = "la contraseña es obligatoria"
            esValido = false
        } else {
            errorPassword=""

        }
        return esValido
    }


}