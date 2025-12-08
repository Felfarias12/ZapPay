package com.example.zappay.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.zappay.model.Usuario
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.request.UsuarioRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    var usuarioLogeadoRut by mutableStateOf("")


    var usuarios by mutableStateOf<List<UsuarioRequest>>(emptyList())
        private set

    var rut by mutableStateOf("")
    var password by mutableStateOf("")

    var errorRut by mutableStateOf("")
    var errorPassword by mutableStateOf("")

    // CARGAR DESDE API
    suspend fun cargarUsuarios() {
        usuarios = withContext(Dispatchers.IO) {
            RetrofitInstance.api.getUsuarios()
        }
    }

    // VALIDAR LOGIN
    fun validarUsuario(): Boolean {



        if (rut.isBlank()) {
            errorRut = "El rut es obligatorio"
            return false
        }
        errorRut = ""

        if (password.isBlank()) {
            errorPassword = "La contraseña es obligatoria"
            return false
        }
        errorPassword = ""

        val usuarioExiste = usuarios.any {
            it.rut == rut && it.contrasenna == password
        }

        if (usuarioExiste) {
            usuarioLogeadoRut = rut   // 🔥 Guarda quién inició sesión
        }


        if (!usuarioExiste) {
            errorPassword = "Rut o contraseña incorrectos"
        } else {
            usuarioLogeadoRut = rut
        }

        return usuarioExiste
    }
}
