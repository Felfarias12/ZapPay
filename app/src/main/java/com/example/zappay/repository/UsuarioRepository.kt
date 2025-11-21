// En UsuarioRepository.kt
package com.example.zappay.repository

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.zappay.model.Usuario
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.request.UsuarioRequest


object UsuarioRepository {
    private var _usuarios by mutableStateOf(listOf<Usuario>())
    val usuarios: List<Usuario> get() = _usuarios
    private var nextId = 1

    suspend fun crearNuevoUsuario(
        nombre: String,
        correo: String,
        edad: String,
        contraseña: String,
        rut: String,
        saldo: Double
    ): Usuario {

        val request = UsuarioRequest(
            Nombre = nombre,
            Correo = correo,
            Edad = edad,
            Contrasenna = contraseña,
            Rut = rut,
            Saldo = saldo
        )

        return RetrofitInstance.api.crearNuevoUsuario(request)
    }

}


